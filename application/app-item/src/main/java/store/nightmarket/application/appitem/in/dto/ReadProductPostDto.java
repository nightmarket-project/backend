package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadProductPostDto {

	@Builder
	public record Response(
		UUID id,
		ProductInfo productInfo,
		Float rating,
		List<ImageManagerInfo> mainImageList,
		List<ImageManagerInfo> detailImageInfoList
	) {

	}

	@Builder
	public record ProductInfo(
		UUID productId,
		String name,
		BigDecimal price,
		String description
	) {

	}

	@Builder
	public record ImageManagerInfo(
		String imageUrl,
		int displayOrder
	) {

	}

}
