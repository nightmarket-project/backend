package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class ReviewImageManager extends ImageManager {

	private final ReviewId reviewId;

	private ReviewImageManager(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType,
		ReviewId reviewId
	) {
		super(
			imageManagerId,
			imageId,
			displayOrder,
			imageType
		);
		this.reviewId = reviewId;
	}

	public static ReviewImageManager newInstance(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType,
		ReviewId reviewId
	) {
		return new ReviewImageManager(
			imageManagerId,
			imageId,
			displayOrder,
			imageType,
			reviewId
		);
	}
	
}
