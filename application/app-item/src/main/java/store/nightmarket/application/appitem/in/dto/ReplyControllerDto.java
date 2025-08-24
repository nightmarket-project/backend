package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

@Builder
public record ReplyControllerDto(
	UserControllerDto user,
	String comment
) {

}
