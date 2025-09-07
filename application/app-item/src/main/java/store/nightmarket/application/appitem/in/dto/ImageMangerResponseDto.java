package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

@Builder
public record ImageMangerResponseDto(
	String url,
	int displayOrder
) {

}
