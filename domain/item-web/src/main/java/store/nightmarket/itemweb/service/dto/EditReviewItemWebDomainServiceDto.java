package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.ReviewContent;

public class EditReviewItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private Review review;
        private UserId authorId;
        private ReviewContent reviewContent;

    }

    @Getter
    @Builder
    public static class Event {

        private Review review;

    }

}
