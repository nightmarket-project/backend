package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Review;

public class DeleteReviewItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private Review review;
        private UserId userId;

    }

    @Getter
    @Builder
    public static class Event {

        private Review review;

    }

}
