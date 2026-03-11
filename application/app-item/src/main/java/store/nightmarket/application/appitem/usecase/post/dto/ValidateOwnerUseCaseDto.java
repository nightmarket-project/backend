package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public class ValidateOwnerUseCaseDto {

	@Builder
	public record Input(
		ProductPostId productPostId,
		UserId userId
	) {

	}

}
