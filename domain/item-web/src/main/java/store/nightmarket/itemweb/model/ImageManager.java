package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@Getter
public class ImageManager extends BaseModel<ImageManagerId> {

	private final Image image;
	private final ImageType imageType;
	private final int displayOrder;
	private final ImageOwnerId ImageOwnerId;

	private ImageManager(
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		this.image = image;
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		ImageOwnerId = imageOwnerId;
	}

	public static ImageManager newInstance(
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		return new ImageManager(
			image,
			imageType,
			displayOrder,
			imageOwnerId
		);
	}

	public ImageManagerId getImageManagerId() {
		return internalId();
	}

}
