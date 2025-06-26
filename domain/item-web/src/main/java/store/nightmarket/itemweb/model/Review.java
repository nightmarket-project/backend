package store.nightmarket.itemweb.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class Review extends BaseModel<ReviewId> {

    private UserId author;
    private PostContent content;
    private Rating rating;
    private List<Reply> replies;

    private Review(
        ReviewId id,
        UserId author,
        PostContent content,
        Rating rating,
        List<Reply> replies
    ) {
        super(id);
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.replies = replies;
    }

    public static Review newInstance(
        ReviewId id,
        UserId author,
        PostContent content,
        Rating rating,
        List<Reply> replies
    ) {
        return new Review(
            id,
            author,
            content,
            rating,
            replies
        );
    }

}
