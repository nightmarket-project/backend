package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public class UnpublishProductPostUseCaseDto {

	@Builder
	public record Input(
		ProductPostId productPostId
	) {

	}

}
