package store.nightmarket.domain.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.Review;
import store.nightmarket.domain.itemweb.valueobject.CommentText;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class EditReviewItemWebDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private Review review;
		private UserId authorId;
		private CommentText commentText;
		private Rating rating;

	}

	@Getter
	@Builder
	public static class Event {

		private Review review;

	}

}
