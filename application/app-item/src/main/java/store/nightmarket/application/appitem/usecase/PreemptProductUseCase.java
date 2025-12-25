package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.constant.Constant.*;
import static store.nightmarket.application.appitem.usecase.dto.PreemptProductUseCaseDto.*;

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
import store.nightmarket.application.appitem.out.ReadPreemptionPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.SavePreemptionPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Preemption;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PreemptProductUseCase implements BaseUseCase<Input, Output> {

	private final ReadProductVariantPort readProductVariantPort;
	private final ReadPreemptionPort readPreemptionPort;
	private final SavePreemptionPort savePreemptionPort;

	@Override
	@DistributedLock(keys = "#input.preemptionProductList.![productVariantId.id]")
	public Output execute(Input input) {

		List<PreemptionProduct> preemptionProducts = input.preemptionProductList().stream()
			.sorted(Comparator.comparing(p -> p.productVariantId().getId()))
			.toList();

		List<ProductVariantId> productVariantIds = preemptionProducts.stream()
			.map(PreemptionProduct::productVariantId)
			.toList();

		Map<ProductVariantId, ProductVariant> productVariantMap =
			readProductVariantPort.readByIdList(productVariantIds).stream()
				.collect(Collectors.toMap(ProductVariant::getProductVariantId, Function.identity()));

		Map<ProductVariantId, Long> preemptedQuantityMap = productVariantIds.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				id -> readPreemptionPort.readPreemptedQuantity(id.getId(), LocalDateTime.now())
			));

		List<ProductVariantId> insufficientProducts =
			getInsufficientProducts(preemptionProducts, productVariantMap, preemptedQuantityMap);

		if (!insufficientProducts.isEmpty()) {
			return Output.builder()
				.isSuccess(false)
				.insufficientProductList(insufficientProducts)
				.build();
		}

		List<Preemption> preemptionList = createPreemptionList(input, preemptionProducts);

		savePreemptionPort.saveAll(preemptionList);

		return Output.builder()
			.isSuccess(true)
			.insufficientProductList(List.of())
			.build();
	}

	private List<ProductVariantId> getInsufficientProducts(
		List<PreemptionProduct> preemptionProducts,
		Map<ProductVariantId, ProductVariant> productVariantMap,
		Map<ProductVariantId, Long> preemptedQuantityMap
	) {
		return preemptionProducts.stream()
			.filter(product -> {
				ProductVariant variant = productVariantMap.get(product.productVariantId());

				long stock = variant.getQuantity().value().longValue();
				long alreadyPreempted = preemptedQuantityMap.get(product.productVariantId());
				long requested = product.quantity().value().longValue();

				return stock < alreadyPreempted + requested;
			})
			.map(PreemptionProduct::productVariantId)
			.toList();
	}

	private List<Preemption> createPreemptionList(
		Input input,
		List<PreemptionProduct> preemptionProducts
	) {
		return preemptionProducts.stream()
			.map(product -> Preemption.newInstance(
				input.orderId(),
				product.productVariantId(),
				product.quantity().value().longValue(),
				LocalDateTime.now().plusMinutes(PREEMPT_TTL_MINUTE)
			))
			.toList();
	}

}
