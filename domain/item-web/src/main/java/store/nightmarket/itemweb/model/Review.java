package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.RegistrantId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

import java.util.List;

public class Review extends BaseModel<ReviewId> {

    private RegistrantId author;
    private PostContent content;
    private int rating;
    private List<Reply> replies;

    private Review(
            ReviewId id,
            RegistrantId author,
            PostContent content,
            int rating,
            List<Reply> replies
    ) {
        super(id);
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.replies = replies;

        validateRating();
    }

    public static Review newInstance(
            ReviewId id,
            RegistrantId author,
            PostContent content,
            int rating,
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

    private void validateRating() {
        if(!(rating >= 0 && rating <= 5)) {
            throw new ItemWebException("Rating must be between 0 and 5");
        }
    }
}
