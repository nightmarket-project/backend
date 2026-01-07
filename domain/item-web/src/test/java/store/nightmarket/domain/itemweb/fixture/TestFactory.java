package store.nightmarket.domain.itemweb.fixture;

import java.util.UUID;

import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.Reply;
import store.nightmarket.domain.itemweb.model.Review;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.ReplyId;
import store.nightmarket.domain.itemweb.model.id.ReviewId;
import store.nightmarket.domain.itemweb.valueobject.CommentText;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class TestFactory {

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
			new Rating(rating)
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
			new ReviewId(reviewId)
		);
	}

}
