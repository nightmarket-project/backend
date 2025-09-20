package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

public class ReadReviewDto {

	@Builder
	public record Response(
		List<ReviewInfo> reviewInfoList
	) {

	}

	@Builder
	public record ReviewInfo(
		UserInfo userInfo,
		CommentText commentText,
		ImageManagerInfo imageManagerInfo,
		Rating rating,
		ReplyInfo replyInfo
	) {

	}

	@Builder
	public record ReplyInfo(
		UserInfo userInfo,
		CommentText commentText
	) {

	}

	@Builder
	public record UserInfo(
		UserId userId,
		Name name
	) {

	}

	@Builder
	public record ImageManagerInfo(
		String url,
		int displayOrder
	) {

	}

}
