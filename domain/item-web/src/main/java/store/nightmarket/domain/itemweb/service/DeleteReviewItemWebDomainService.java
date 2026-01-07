package store.nightmarket.domain.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.Review;
import store.nightmarket.domain.itemweb.service.dto.DeleteReviewItemWebDomainServiceDto.Event;
import store.nightmarket.domain.itemweb.service.dto.DeleteReviewItemWebDomainServiceDto.Input;

public class DeleteReviewItemWebDomainService
	implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		Review review = input.getReview();
		UserId currentUserId = input.getUserId();

		review.delete(currentUserId);

		return Event.builder()
			.review(review)
			.build();
	}

}
