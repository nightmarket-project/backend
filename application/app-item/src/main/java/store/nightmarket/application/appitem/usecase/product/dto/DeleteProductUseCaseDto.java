package store.nightmarket.application.appitem.usecase.product.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteProductUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		UserId userId
	) {

	}

}
