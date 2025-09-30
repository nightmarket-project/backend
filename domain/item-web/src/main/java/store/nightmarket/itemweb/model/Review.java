package store.nightmarket.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Review extends ImageOwnerModel<ReviewId> {

	private final ProductPostId postId;
	private final UserId author;
	private CommentText commentText;
	private Rating rating;
	private boolean deleted = false;

	private Review(
		ReviewId id,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		super(
			id,
			ImageOwnerType.REVIEW
		);
		this.postId = postId;
		this.author = author;
		this.commentText = commentText;
		this.rating = rating;
	}

	private Review(
		ReviewId id,
		LocalDateTime createdAt,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		super(
			id,
			createdAt,
			ImageOwnerType.REVIEW
		);
		this.postId = postId;
		this.author = author;
		this.commentText = commentText;
		this.rating = rating;
	}

	public static Review newInstance(
		ReviewId id,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		return new Review(
			id,
			postId,
			author,
			commentText,
			rating
		);
	}

	public static Review newInstanceWithCreatedAt(
		ReviewId id,
		LocalDateTime createdAt,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		return new Review(
			id,
			createdAt,
			postId,
			author,
			commentText,
			rating
		);
	}

	public void delete(UserId currentUserId) {
		if (deleted) {
			throw new ItemWebException("이미 삭제된 댓글입니다.");
		}
		if (!currentUserId.equals(author)) {
			throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
		}

		this.commentText = CommentText.createDeletedComment();
		this.deleted = true;
	}

	public void edit(
		UserId authorId,
		CommentText editContent,
		Rating editRating
	) {
		if (!authorId.equals(this.author)) {
			throw new ItemWebException("댓글 작성자만 수정 가능합니다.");
		}

		this.commentText = (editContent == null) ? commentText : editContent;
		this.rating = (editRating == null) ? rating : editRating;
	}

	public ReviewId getReviewId() {
		return internalId();
	}

}
