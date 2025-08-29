package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

@Builder
public record ImageMangerControllerDto(
	String url,
	int displayOrder
) {

}
