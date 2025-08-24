package store.nightmarket.application.appitem.in.dto;

import java.util.UUID;

import lombok.Builder;
import store.nightmarket.itemweb.valueobject.Rating;

@Builder
public record ProductPostControllerDto(
	UUID productPostId,
	ProductControllerDto productController,
	Rating rating
) {

}
