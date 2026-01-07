package store.nightmarket.domain.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.Review;
import store.nightmarket.domain.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Event;
import store.nightmarket.domain.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Input;
import store.nightmarket.domain.itemweb.valueobject.CommentText;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class EditReviewItemWebDomainService
	implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		Review review = input.getReview();
		UserId authorId = input.getAuthorId();
		CommentText commentText = input.getCommentText();
		Rating rating = input.getRating();

		review.edit(
			authorId,
			commentText,
			rating
		);

		return Event.builder()
			.review(review)
			.build();
	}

}
