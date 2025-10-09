package store.nightmarket.application.appitem.usecase;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.aop.DistributedLock;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductStockValidator {

	private static final String VARIANT_STOCK_PREFIX = "stock:";
	private static final String VARIANT_RESERVED_PREFIX = "reserved:";
	private static final String VARIANT_RESERVED_USER_PREFIX = "reserved_user:";
	private static final Duration RESERVATION_TTL = Duration.ofMinutes(15);

	private final ReadProductVariantPort readProductVariantPort;
	private final RedisTemplate<String, String> redisTemplate;

	@DistributedLock(key = "#productVariantId")
	public void validateAndReserveStock(
		ProductVariantId productVariantId,
		Quantity requestedQuantity,
		UserId userId
	) {
		log.info("재고 검증 시작 - VariantId: {}, 요청수량: {}", productVariantId, requestedQuantity);

		Quantity totalStock = getTotalStock(productVariantId)
			.orElseGet(() -> loadAndCacheStockFromDatabase(productVariantId));

		Quantity availableStock = totalStock.subtract(getReservedStock(productVariantId));

		if (availableStock.isLessThan(requestedQuantity)) {
			String message = String.format(
				"재고 부족 - VariantId: %s, 요청: %s, 가용: %s",
				productVariantId, requestedQuantity, availableStock
			);
			throw new ItemWebException(message);
		}

		increaseReservedQuantity(productVariantId, requestedQuantity);
		recordReservationUser(productVariantId, userId);

		log.info("재고 예약 완료 - VariantId: {}", productVariantId);
	}

	@DistributedLock(key = "#productVariantId")
	public void rollbackReservation(
		ProductVariantId productVariantId,
		Quantity rollbackQuantity,
		UserId userId
	) {
		log.info("재고 롤백 시작 - VariantId: {}, 롤백수량: {}", productVariantId, rollbackQuantity);

		if (isReserved(productVariantId, userId)) {
			cancelReservationUser(productVariantId, userId);
			decreaseReservedQuantity(productVariantId, rollbackQuantity);
		}

		log.info("재고 롤백 완료 - VariantId: {}", productVariantId);
	}

	private Optional<Quantity> getTotalStock(ProductVariantId productVariantId) {
		String stock = redisTemplate.opsForValue()
			.get(VARIANT_STOCK_PREFIX + productVariantId.toString());

		return stock != null ? Optional.of(new Quantity(BigDecimal.valueOf(Long.parseLong(stock)))) : Optional.empty();
	}

	private Quantity getReservedStock(ProductVariantId productVariantId) {
		String reserved = redisTemplate.opsForValue()
			.get(VARIANT_RESERVED_PREFIX + productVariantId.toString());
		return new Quantity(BigDecimal.valueOf(reserved != null ? Long.parseLong(reserved) : 0));
	}

	private void increaseReservedQuantity(
		ProductVariantId productVariantId,
		Quantity quantity
	) {
		String key = VARIANT_RESERVED_PREFIX + productVariantId.toString();
		redisTemplate.opsForValue()
			.increment(key, quantity.value().longValue());
		redisTemplate.expire(key, RESERVATION_TTL);
	}

	private void decreaseReservedQuantity(
		ProductVariantId productVariantId,
		Quantity quantity
	) {
		String key = VARIANT_RESERVED_PREFIX + productVariantId.toString();
		redisTemplate.opsForValue()
			.decrement(key, quantity.value().longValue());
		redisTemplate.expire(key, RESERVATION_TTL);
	}

	private void recordReservationUser(
		ProductVariantId productVariantId,
		UserId userId
	) {
		String key = VARIANT_RESERVED_USER_PREFIX + productVariantId.toString();
		String value = userId.toString();
		redisTemplate.opsForSet().add(key, value);
		redisTemplate.expire(key, RESERVATION_TTL);
	}

	private void cancelReservationUser(
		ProductVariantId productVariantId,
		UserId userId
	) {
		String key = VARIANT_RESERVED_USER_PREFIX + productVariantId.toString();
		String value = userId.toString();
		redisTemplate.opsForSet().remove(key, value);
	}

	private Boolean isReserved(
		ProductVariantId productVariantId,
		UserId userId
	) {
		String key = VARIANT_RESERVED_USER_PREFIX + productVariantId.toString();
		String value = userId.toString();
		return redisTemplate.opsForSet().isMember(key, value);
	}

	private void cacheTotalStock(ProductVariantId productVariantId, Quantity quantity) {
		String key = VARIANT_STOCK_PREFIX + productVariantId.toString();
		redisTemplate.opsForValue().append(key, String.valueOf(quantity.value().longValue()));
		redisTemplate.expire(key, RESERVATION_TTL);
	}

	private Quantity loadAndCacheStockFromDatabase(ProductVariantId variantId) {
		ProductVariant productVariant = readProductVariantPort.readOrThrow(variantId);
		Quantity totalStock = productVariant.getQuantity();
		cacheTotalStock(variantId, totalStock);
		return totalStock;
	}

}


