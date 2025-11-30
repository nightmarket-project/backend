package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ValidateProductStockUseCaseDto.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.aop.DistributedLock;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.exception.ItemWebException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ValidateProductStockUseCase implements BaseUseCase<Input, Void> {

	private static final String STOCK_CACHE_KEY_PREFIX = "stock:";
	private static final Duration STOCK_CACHE_TTL = Duration.ofMinutes(15);

	private final ReadProductVariantPort readProductVariantPort;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	@DistributedLock(
		keys = {"#input.checkProductList.![productVariantId]"}
	)
	public Void execute(Input input) {
		String threadName = Thread.currentThread().getName();
		log.debug("[{}] UseCase 실행 시작", threadName); // 실행 시작 로깅

		Map<ProductVariantId, Quantity> stockMap = getAvailableStock(input.checkProductList());
		List<String> errors = new ArrayList<>();

		for (ProductQuantityDto product : input.checkProductList()) {
			try {
				Quantity currentStock = stockMap.get(product.productVariantId());
				Quantity remainingStock = currentStock.subtract(product.quantity());
				stockMap.replace(product.productVariantId(), remainingStock);
			} catch (Exception e) {
				errors.add(product.productVariantId().toString());
			}
		}

		if (!errors.isEmpty()) {
			throw new ItemWebException(String.join("\n", errors));
		}

		stockMap.forEach(this::cacheUpdatedStock);
		return null;
	}

	private Map<ProductVariantId, Quantity> getAvailableStock(List<ProductQuantityDto> productQuantityDtoList) {
		List<ProductVariantId> variantIdList = productQuantityDtoList.stream()
			.map(ProductQuantityDto::productVariantId)
			.toList();
		Map<ProductVariantId, Quantity> stockMap = new HashMap<>();
		List<ProductVariantId> cacheMissIdList = new ArrayList<>();

		for (ProductVariantId variantId : variantIdList) {
			getStockFromCache(variantId)
				.ifPresentOrElse(
					stock -> stockMap.put(variantId, stock),
					() -> cacheMissIdList.add(variantId));
		}

		if (!cacheMissIdList.isEmpty()) {
			stockMap.putAll(loadStockFromDB(cacheMissIdList));
		}

		return stockMap;
	}

	private Optional<Quantity> getStockFromCache(ProductVariantId productVariantId) {
		String stock = redisTemplate.opsForValue()
			.get(STOCK_CACHE_KEY_PREFIX + productVariantId.toString());

		return stock != null ? Optional.of(new Quantity(BigDecimal.valueOf(Long.parseLong(stock)))) : Optional.empty();
	}

	private void cacheUpdatedStock(
		ProductVariantId productVariantId,
		Quantity quantity
	) {
		String key = STOCK_CACHE_KEY_PREFIX + productVariantId.toString();
		redisTemplate.opsForValue()
			.set(key, String.valueOf(quantity.value().longValue()), STOCK_CACHE_TTL);
	}

	private Map<ProductVariantId, Quantity> loadStockFromDB(List<ProductVariantId> productVariantIdList) {
		Map<ProductVariantId, Quantity> stockMap = new HashMap<>();
		List<ProductVariant> dbProductVariantList = readProductVariantPort.readByIdList(productVariantIdList);

		for (ProductVariant productVariant : dbProductVariantList) {
			stockMap.put(
				productVariant.getProductVariantId(),
				productVariant.getQuantity()
			);
		}

		return stockMap;
	}

}
