package store.nightmarket.itemweb.valueobject;

import java.util.Objects;
import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class Content {

    private final static int MAX_TEXT_LENGTH = 255;

    private String text;

    public Content(
        String text
    ) {
        validate(text);
        this.text = text;
    }

    private void validate(String text) {
        if (text.isBlank()) {
            throw new ItemWebException("Text is blank");
        }
        if (text.length() > MAX_TEXT_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
    }

    public static Content deleted() {
        return new Content("삭제된 댓글 입니다.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Content content = (Content) obj;
        return Objects.equals(text, content.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

}
