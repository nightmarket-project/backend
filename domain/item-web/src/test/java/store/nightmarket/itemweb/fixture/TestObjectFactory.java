package store.nightmarket.itemweb.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class TestObjectFactory {

	public static Review createReview(
		UUID reviewId,
		UUID productPostId,
		UUID authorId,
		String commentText,
		int rating
	) {
		return Review.newInstance(
			new ReviewId(reviewId),
			new ProductPostId(productPostId),
			new UserId(authorId),
			new CommentText(commentText),
			new Rating(rating),
			LocalDateTime.now()
		);
	}

	public static Reply createReply(
		UUID replyId,
		String commentText,
		UUID authorId,
		UUID reviewId
	) {
		return Reply.newInstance(
			new ReplyId(replyId),
			new CommentText(commentText),
			new UserId(authorId),
			new ReviewId(reviewId),
			LocalDateTime.now()
		);
	}

}
