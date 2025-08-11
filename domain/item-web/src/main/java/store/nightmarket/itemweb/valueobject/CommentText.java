package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class CommentText {

	private static final int MAX_DESCRIPTION_LENGTH = 200;

	private final String value;
	private final boolean deleted;

	public CommentText(String value) {
		this(value, false);
	}

	public CommentText(
		String value,
		boolean deleted
	) {
		validate(value);
		this.value = value;
		this.deleted = deleted;
	}

	private void validate(String value) {
		if (value.length() > MAX_DESCRIPTION_LENGTH) {
			throw new ItemWebException("Text is too long");
		}
	}

	public static CommentText createDeletedComment() {
		return new CommentText("삭제된 댓글입니다.", true);
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
