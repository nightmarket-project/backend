package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.valueobject.CommentText;

public class EditReplyItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private Reply reply;
        private UserId userId;
        private CommentText commentText;

    }

    @Getter
    @Builder
    public static class Event {

        private Reply reply;

    }

}
