package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadReviewDto {

	@Builder
	public record Response(
		List<ReviewInfo> reviewList
	) {

	}

	@Builder
	public record ReviewInfo(
		UserInfo user,
		String comment,
		String imageUrl,
		Float rating,
		ReplyInfo replyInfo
	) {

	}

	@Builder
	public record ReplyInfo(
		UserInfo user,
		String comment
	) {

	}

	@Builder
	public record UserInfo(
		UUID userId,
		String name
	) {

	}

}
