package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

public class ReadProductPostResponseDto {

	@Builder
	public record Response(
		ProductPostId id,
		ProductControllerDto productControllerDto,
		Rating rating,
		List<ImageMangerResponseDto> detailImageList
	) {

	}

	@Builder
	public record ProductControllerDto(
		ProductId productId,
		Name name,
		Price price,
		String description,
		List<ImageMangerResponseDto> mainImageList
	) {

	}

}
