package store.nightmarket.application.appitem.usecase.preempt;

import static store.nightmarket.application.appitem.constant.Constant.*;
import static store.nightmarket.application.appitem.usecase.preempt.dto.PreemptProductVariantUseCaseDto.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.aop.DistributedLock;
import store.nightmarket.application.appitem.out.preempt.ReadPreemptedProductVariantPort;
import store.nightmarket.application.appitem.out.preempt.SavePreemptedProductVariantPort;
import store.nightmarket.application.appitem.out.variant.ReadProductVariantPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.PreemptedProductVariant;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PreemptProductVariantUseCase implements BaseUseCase<Input, Output> {

	private final ReadProductVariantPort readProductVariantPort;
	private final ReadPreemptedProductVariantPort readPreemptedProductVariantPort;
	private final SavePreemptedProductVariantPort savePreemptedProductVariantPort;

	@Override
	@DistributedLock(keys = "#input.preemptRequestedProductList.![productVariantId.id]")
	public Output execute(Input input) {
		List<PreemptRequestedProduct> preemptRequestedProducts = input.preemptRequestedProductList().stream()
			.sorted(Comparator.comparing(p -> p.productVariantId().getId()))
			.toList();

		List<ProductVariantId> productVariantIds = preemptRequestedProducts.stream()
			.map(PreemptRequestedProduct::productVariantId)
			.toList();

		Map<ProductVariantId, ProductVariant> productVariantMap =
			readProductVariantPort.readByIdList(productVariantIds).stream()
				.collect(Collectors.toMap(ProductVariant::getProductVariantId, Function.identity()));

		Map<ProductVariantId, Long> preemptedQuantityMap = productVariantIds.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				id -> readPreemptedProductVariantPort.readPreemptedQuantity(id.getId(), LocalDateTime.now())
			));

		List<ProductVariantId> insufficientProducts =
			getInsufficientProducts(preemptRequestedProducts, productVariantMap, preemptedQuantityMap);

		if (!insufficientProducts.isEmpty()) {
			return Output.builder()
				.isSuccess(false)
				.insufficientProductList(insufficientProducts)
				.build();
		}

		List<PreemptedProductVariant> preemptedProductVariantList =
			createPreemptedProductVariantList(input, preemptRequestedProducts);

		savePreemptedProductVariantPort.saveAll(preemptedProductVariantList);

		return Output.builder()
			.isSuccess(true)
			.insufficientProductList(List.of())
			.build();
	}

	private List<ProductVariantId> getInsufficientProducts(
		List<PreemptRequestedProduct> preemptRequestedProducts,
		Map<ProductVariantId, ProductVariant> productVariantMap,
		Map<ProductVariantId, Long> preemptedQuantityMap
	) {
		return preemptRequestedProducts.stream()
			.filter(product -> {
				ProductVariant variant = productVariantMap.get(product.productVariantId());

				long stock = variant.getQuantity().value().longValue();
				long alreadyPreempted = preemptedQuantityMap.get(product.productVariantId());
				long requested = product.quantity().value().longValue();

				return stock < alreadyPreempted + requested;
			})
			.map(PreemptRequestedProduct::productVariantId)
			.toList();
	}

	private List<PreemptedProductVariant> createPreemptedProductVariantList(
		Input input,
		List<PreemptRequestedProduct> preemptRequestedProducts
	) {
		return preemptRequestedProducts.stream()
			.map(product -> PreemptedProductVariant.newInstance(
				input.orderId(),
				product.productVariantId(),
				product.quantity().value().longValue(),
				LocalDateTime.now().plusMinutes(PREEMPT_TTL_MINUTE)
			))
			.toList();
	}

}
