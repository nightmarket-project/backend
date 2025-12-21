package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class SearchProductDto {

	@Builder
	public record Response(
		List<ProductInfo> contents,
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
		String imageUrl,
		String name,
		BigInteger price,
		Float rating
	) {

	}

}
