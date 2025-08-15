package store.nightmarket.itemweb.model;

import java.time.LocalDate;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Reply extends BaseModel<ReplyId> {

	private CommentText commentText;
	private final UserId authorId;
	private final ReviewId reviewId;
	private final LocalDate createdAt;
	private boolean deleted;

	private Reply(
		ReplyId id,
		CommentText commentText,
		UserId authorId,
		ReviewId reviewId
	) {
		super(id);
		this.commentText = commentText;
		this.authorId = authorId;
		this.reviewId = reviewId;
		createdAt = LocalDate.now();
		deleted = false;
	}

	public static Reply newInstance(
		ReplyId id,
		CommentText commentText,
		UserId authorId,
		ReviewId reviewId
	) {
		return new Reply(
			id,
			commentText,
			authorId,
			reviewId
		);
	}

	public void delete(UserId currentUserId) {
		if (deleted) {
			throw new ItemWebException("이미 삭제된 댓글입니다.");
		}
		if (!currentUserId.equals(authorId)) {
			throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
		}

		commentText = CommentText.createDeletedComment();
		deleted = true;
	}

	public void edit(
		UserId userId,
		CommentText editText
	) {
		if (!userId.equals(authorId)) {
			throw new ItemWebException("댓글 작성자만 수정 가능합니다.");
		}
		this.commentText = editText;
	}

	public ReplyId getReplyId() {
		return internalId();
	}

}
