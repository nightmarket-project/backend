package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

public class ReviewListControllerDto {

	@Builder
	public record Response(
		List<ReviewControllerDto> reviewControllerDtoList
	) {

	}

	@Builder
	public record ReviewControllerDto(
		UserControllerDto userControllerDto,
		CommentText commentText,
		ImageMangerControllerDto imageMangerControllerDto,
		Rating rating,
		ReplyControllerDto replyControllerDto
	) {

	}

	@Builder
	public record ReplyControllerDto(
		UserControllerDto userControllerDto,
		CommentText commentText
	) {

	}

}
