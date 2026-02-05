package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class UpdateProductPostUseCaseDto {

	@Builder
	public record Input(
		ProductPostId productPostId,
		Rating rating
	) {

	}
}
