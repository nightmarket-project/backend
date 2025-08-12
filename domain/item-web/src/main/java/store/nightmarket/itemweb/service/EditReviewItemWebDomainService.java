package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Image;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Input;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

public class EditReviewItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Review review = input.getReview();
        UserId authorId = input.getAuthorId();
        CommentText commentText = input.getCommentText();
        Rating rating = input.getRating();
        Image image = input.getImage();

        review.edit(
            authorId,
            commentText,
            rating,
            image
        );

        return Event.builder()
            .review(review)
            .build();
    }

}
