package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

@Getter
public class ProductPostImageManager extends ImageManager {

	private final ProductPostId productPostId;

	private ProductPostImageManager(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType,
		ProductPostId productPostId
	) {
		super(
			imageManagerId,
			imageId,
			displayOrder,
			imageType
		);
		this.productPostId = productPostId;
	}

	public static ProductPostImageManager newInstance(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType,
		ProductPostId productPostId
	) {
		return new ProductPostImageManager(
			imageManagerId,
			imageId,
			displayOrder,
			imageType,
			productPostId
		);
	}

}
