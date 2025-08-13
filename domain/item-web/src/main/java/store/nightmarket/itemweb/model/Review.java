package store.nightmarket.itemweb.model;

import java.time.LocalDate;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Review extends ImageOwnerModel<ReviewId> {

	private final ProductPostId postId;
	private final UserId author;
	private CommentText commentText;
	private Rating rating;
	private final LocalDate createdAt;
	private boolean deleted = false;

	public Review(
		ReviewId id,
		ImageOwnerId imageOwnerId,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		super(
			id,
			imageOwnerId,
			ImageOwnerType.REVIEW
		);
		this.postId = postId;
		this.author = author;
		this.commentText = commentText;
		this.rating = rating;
		this.createdAt = LocalDate.now();
	}

	public static Review newInstance(
		ReviewId id,
		ImageOwnerId imageOwnerId,
		ProductPostId postId,
		UserId author,
		CommentText commentText,
		Rating rating
	) {
		return new Review(
			id,
			imageOwnerId,
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
		deleted = true;
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

}
