package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class CommentText {

	private static final int MAX_DESCRIPTION_LENGTH = 200;

	private final String value;

	public CommentText(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		if (value.length() > MAX_DESCRIPTION_LENGTH) {
			throw new ItemWebException("Text is too long");
		}
	}

	public static CommentText createDeletedComment() {
		return new CommentText("삭제된 댓글입니다.");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CommentText that = (CommentText)obj;
		return Objects.equals(value, that.value);
	}

}
