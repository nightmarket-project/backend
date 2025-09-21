package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public class ReadProductPostDto {

	@Builder
	public record Response(
		ProductPostId id,
		ProductInfo productInfo,
		Float rating,
		List<ImageManagerInfo> detailImageInfoList
	) {

	}

	@Builder
	public record ProductInfo(
		ProductId productId,
		String name,
		BigDecimal price,
		String description,
		List<ImageManagerInfo> mainImageList
	) {

	}

	@Builder
	public record ImageManagerInfo(
		String url,
		int displayOrder
	) {

	}

}
