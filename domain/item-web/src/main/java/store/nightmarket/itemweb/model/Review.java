package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import java.util.ArrayList;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class Review extends BaseModel<ReviewId> {

    private UserId author;
    private Content content;
    private Rating rating;
    private Reply reply;
    private final LocalDate createdAt;
    private boolean deleted;

    private Review(
        ReviewId id,
        UserId author,
        Content content,
        Rating rating,
        Reply reply
    ) {
        super(id);
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.reply = reply;
        this.createdAt = LocalDate.now();
        deleted = false;
    }

    public static Review newInstance(
        ReviewId id,
        UserId author,
        Content content,
        Rating rating,
        Reply reply
    ) {
        return new Review(
            id,
            author,
            content,
            rating,
            reply
        );
    }


    public void delete(UserId currentUserId) {
        if(deleted) {
           throw new ItemWebException("이미 삭제된 댓글입니다.");
        }
        if(!currentUserId.equals(author)) {
            throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
        }
    }
}
