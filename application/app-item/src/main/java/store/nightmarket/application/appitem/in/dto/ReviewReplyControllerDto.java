package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

@Builder
public record ReviewReplyControllerDto(
	ReviewControllerDto reviewControllerDto,
	ReplyControllerDto replyControllerDto
) {

}
