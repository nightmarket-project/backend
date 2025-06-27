package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class Review extends BaseModel<ReviewId> {

    private UserId author;
    private PostContent content;
    private Rating rating;
    private Reply reply;
    private final LocalDate createdAt;
    private boolean deleted;

    private Review(
        ReviewId id,
        UserId author,
        PostContent content,
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
        PostContent content,
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



}
