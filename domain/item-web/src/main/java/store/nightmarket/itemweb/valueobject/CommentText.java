package store.nightmarket.itemweb.valueobject;

import java.util.Objects;
import store.nightmarket.itemweb.exception.ItemWebException;

public record CommentText(String value) {

    private static final int MAX_DESCRIPTION_LENGTH = 200;

    public CommentText {
        validate(value);
    }

    private void validate(String value) {
        if (value.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
    }

    public static CommentText deleted() {
        return new CommentText("삭제된 댓글입니다.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CommentText that = (CommentText) obj;
        return Objects.equals(value, that.value);
    }

}
