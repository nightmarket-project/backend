package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class Reply extends BaseModel<ReplyId> {

    private Content content;
    private UserId author;
    private ReviewId reviewId;

    private Reply(
        ReplyId id,
        Content content,
        UserId author,
        ReviewId reviewId
    ) {
        super(id);
        this.content = content;
        this.author = author;
        this.reviewId = reviewId;
    }

    public static Reply newInstance(
        ReplyId id,
        Content content,
        UserId author,
        ReviewId reviewId
    ) {
        return new Reply(
            id,
            content,
            author,
            reviewId
        );
    }

}
