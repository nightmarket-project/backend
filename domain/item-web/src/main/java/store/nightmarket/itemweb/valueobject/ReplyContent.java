package store.nightmarket.itemweb.valueobject;

import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class ReplyContent {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private String description;

    public ReplyContent(String description) {
        validte(description);
        this.description = description;
    }

    private void validte(String description) {
        if (description.isBlank()) {
            throw new ItemWebException("Text is blank");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
    }

    public static ReplyContent deleted() {
        return new ReplyContent("삭제된 댓글입니다.");
    }

}
