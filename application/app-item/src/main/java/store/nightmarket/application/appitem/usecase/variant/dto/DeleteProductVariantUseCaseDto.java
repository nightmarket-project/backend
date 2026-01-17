package store.nightmarket.application.appitem.usecase.variant.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteProductVariantUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		ProductVariantId productVariantId,
		UserId userId
	) {

	}

}
