package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class PreemptProductVariantUseCaseDto {

	@Builder
	public record Input(
		OrderId orderId,
		List<PreemptRequestedProduct> preemptRequestedProductList
	) {

	}

	@Builder
	public record PreemptRequestedProduct(
		ProductVariantId productVariantId,
		Quantity quantity
	) {

	}

	@Builder
	public record Output(
		boolean isSuccess,
		List<ProductVariantId> insufficientProductList
	) {

	}

}
