package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.itemweb.valueobject.Image;

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
		String name,
		BigDecimal price,
		Float rating
	) {

	}

}
