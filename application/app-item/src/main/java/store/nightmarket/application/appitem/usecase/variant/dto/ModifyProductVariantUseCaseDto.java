package store.nightmarket.application.appitem.usecase.variant.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ModifyProductVariantUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		ProductVariantId productVariantId,
		UserId userId,
		String SKUCode,
		Quantity quantity
	) {

	}

}
