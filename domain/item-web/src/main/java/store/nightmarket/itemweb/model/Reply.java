package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class Reply extends BaseModel<ReviewId> {

    private String content;
    private UserId author;
    private static int MAX_LENGTH = 255;

    private Reply(
            ReviewId id,
            String content,
            UserId author
    ) {
        super(id);
        this.content = content;
        this.author = author;
        validateContent();
    }

    public static Reply newInstance(
            ReviewId id,
            String content,
            UserId author
    ) {
        return new Reply(
                id,
                content,
                author
        );
    }

    void validateContent() {
        if (content.isBlank()) {
            throw new ItemWebException("The content is blank");
        }
        if (content.length() > MAX_LENGTH) {
            throw new ItemWebException("The content is too long");
        }
    }

}
