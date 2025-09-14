package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.Rating;

public class SearchProductDto {

	@Builder
	public record Response(
		List<ProductInfo> content,
		int currentPage,
		int numberOfElements,
		int totalPage,
		long totalElements,
		boolean hasNext
	) {

	}

	@Builder
	public record ProductInfo(
		UUID productPostId,
		Image image,
		Name name,
		Price price,
		Rating rating
	) {

	}

}
