package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

public class ReviewResponseDto {

	@Builder
	public record Response(
		List<ReviewControllerDto> reviewControllerDtoList
	) {

	}

	@Builder
	public record ReviewControllerDto(
		UserResponseDto userResponseDto,
		CommentText commentText,
		ImageMangerResponseDto imageMangerResponseDto,
		Rating rating,
		ReplyControllerDto replyControllerDto
	) {

	}

	@Builder
	public record ReplyControllerDto(
		UserResponseDto userResponseDto,
		CommentText commentText
	) {

	}

}
