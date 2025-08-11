package store.nightmarket.itemweb.fixture;

import java.util.Random;
import java.util.UUID;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Image;
import store.nightmarket.itemweb.model.ProductPostImageManager;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.model.ReviewImageManager;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
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
		ReviewImageManager reviewImageManager,
		int rating
	) {
		return Review.newInstance(
			new ReviewId(reviewId),
			new ProductPostId(productPostId),
			new UserId(authorId),
			new CommentText(commentText),
			reviewImageManager,
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
			new ReviewId(replyId)
		);
	}

	public static Image createImage(
		UUID imageId,
		String imageUrl,
		String altText
	) {
		return Image.newInstance(
			new ImageId(imageId),
			imageUrl,
			altText
		);
	}

	public static Image defaultImage(UUID imageId) {
		Random random = new Random();
		return Image.newInstance(
			new ImageId(imageId),
			"https://picsum.photos/" + random.nextInt() + "/" + random.nextInt(),
			"배경 사진"
		);
	}

	public static ReviewImageManager createReviewImageManager(
		UUID imageManagerId,
		UUID imageId,
		int displayOrder,
		ImageType imageType,
		UUID reviewId
	) {
		return ReviewImageManager.newInstance(
			new ImageManagerId(imageManagerId),
			new ImageId(imageId),
			displayOrder,
			imageType,
			new ReviewId(reviewId)
		);
	}

	public static ProductPostImageManager createProductPostImageManager(
		UUID imageManagerId,
		UUID imageId,
		int displayOrder,
		ImageType imageType,
		UUID productPostId
	) {
		return ProductPostImageManager.newInstance(
			new ImageManagerId(imageManagerId),
			new ImageId(imageId),
			displayOrder,
			imageType,
			new ProductPostId(productPostId)
		);
	}

}
