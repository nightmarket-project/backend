package store.nightmarket.itemweb.fixture;

import java.util.List;
import java.util.UUID;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class TestObjectFactory {

	public static Review createReview(
		UUID reviewId,
		UUID imageOwnerId,
		List<ImageManager> imageManagerList,
		UUID productPostId,
		UUID authorId,
		String commentText,
		int rating
	) {
		return Review.newInstance(
			new ReviewId(reviewId),
			new ImageOwnerId(imageOwnerId),
			imageManagerList,
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

	public static List<ImageManager> defaultImageManagerList(UUID imageOwnerId) {
		return List.of(
			ImageManager.newInstance(
				new Image("https://picsum.photos/100"),
				ImageType.MAIN,
				1,
				new ImageOwnerId(imageOwnerId)
			)
		);
	}

}
