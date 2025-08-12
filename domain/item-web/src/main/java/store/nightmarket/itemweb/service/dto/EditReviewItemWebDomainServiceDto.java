package store.nightmarket.itemweb.service.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

public class EditReviewItemWebDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private Review review;
		private UserId authorId;
		private CommentText commentText;
		private Rating rating;
		private List<ImageManager> imageManagerList;

	}

	@Getter
	@Builder
	public static class Event {

		private Review review;

	}

}
