package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

public class ReadProductPostDto {

	@Builder
	public record Response(
		ProductPostId id,
		ProductInfo productInfo,
		Rating rating,
		List<ImageManagerInfo> detailImageInfoList
	) {

	}

	@Builder
	public record ProductInfo(
		ProductId productId,
		Name name,
		Price price,
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
