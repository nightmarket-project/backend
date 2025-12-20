package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class PreemptProductUseCaseDto {

	@Builder
	public record Input(
		OrderId orderId,
		List<PreemptionProduct> preemptionProductList
	) {

	}

	@Builder
	public record PreemptionProduct(
		ProductVariantId productVariantId,
		Quantity quantity
	) {

	}

	@Builder
	public record Output(
		boolean isSuccess,
		List<UUID> insufficientProductList
	) {

	}

}
