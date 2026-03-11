package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class RegisterProductPostUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		UserId userId
	) {

	}

}
