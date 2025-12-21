package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.constant.Constant.*;
import static store.nightmarket.application.appitem.usecase.dto.PreemptProductUseCaseDto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

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

		List<PreemptionProduct> products = input.preemptionProductList().stream()
			.sorted(Comparator.comparing(p -> p.productVariantId().getId()))
			.toList();

		List<UUID> insufficientProducts = new ArrayList<>();

		for (PreemptionProduct product : products) {
			validatePreemptable(product, insufficientProducts);
		}

		if (!insufficientProducts.isEmpty()) {
			return Output.builder()
				.isSuccess(false)
				.insufficientProductList(insufficientProducts)
				.build();
		}

		for (PreemptionProduct product : products) {
			savePreemption(
				input.orderId(),
				product.productVariantId(),
				product.quantity()
			);
		}

		return Output.builder()
			.isSuccess(true)
			.insufficientProductList(List.of())
			.build();
	}

	private void validatePreemptable(
		PreemptionProduct product,
		List<UUID> insufficientProducts
	) {
		ProductVariant productVariant = readProductVariantPort.readOrThrow(product.productVariantId());

		long stock = productVariant.getQuantity().value().longValue();

		long preemptedQuantity = readPreemptionPort.readPreemptedQuantity(
			product.productVariantId().getId(),
			LocalDateTime.now()
		);

		if (stock < preemptedQuantity + product.quantity().value().longValue()) {
			insufficientProducts.add(product.productVariantId().getId());
		}
	}

	private void savePreemption(
		OrderId orderId,
		ProductVariantId productVariantId,
		Quantity quantity
	) {
		savePreemptionPort.save(
			Preemption.newInstance(
				orderId,
				productVariantId,
				quantity.value().longValue(),
				LocalDateTime.now().plusMinutes(PREEMPT_TTL)
			)
		);
	}

}
