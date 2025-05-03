package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.RegistrantId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

import java.util.List;

public class Review extends BaseModel<ReviewId> {

    private RegistrantId author;
    private PostContent content;
    private List<Reply> replies;

    private Review(
            ReviewId id,
            RegistrantId author,
            PostContent content,
            List<Reply> replies
    ) {
        super(id);
        this.author = author;
        this.content = content;
        this.replies = replies;
    }

    public static Review newInstance(
            ReviewId id,
            RegistrantId author,
            PostContent content,
            List<Reply> replies
    ) {
        return new Review(
                id,
                author,
                content,
                replies
        );
    }
}
