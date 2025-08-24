package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

@Builder
public record ReviewControllerDto(
	UserControllerDto user,
	String commentText,
	ImageMangerControllerDto image,
	Float rating
) {

}
