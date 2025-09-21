package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.UserId;

public class ReadReviewDto {

	@Builder
	public record Response(
		List<ReviewInfo> reviewInfoList
	) {

	}

	@Builder
	public record ReviewInfo(
		UserInfo userInfo,
		String commentText,
		ImageManagerInfo imageManagerInfo,
		Float rating,
		ReplyInfo replyInfo
	) {

	}

	@Builder
	public record ReplyInfo(
		UserInfo userInfo,
		String commentText
	) {

	}

	@Builder
	public record UserInfo(
		UserId userId,
		String name
	) {

	}

	@Builder
	public record ImageManagerInfo(
		String url,
		int displayOrder
	) {

	}

}
