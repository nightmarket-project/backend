package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;

@Getter
public class ImageManager extends BaseModel<ImageManagerId> {

	private ImageId imageId;
	private int displayOrder;
	private final ImageType imageType;

	private ImageManager(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType
	) {
		super(imageManagerId);
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		this.imageId = imageId;
	}

	public static ImageManager newInstance(
		ImageManagerId imageManagerId,
		ImageId imageId,
		int displayOrder,
		ImageType imageType
	) {
		return new ImageManager(
			imageManagerId,
			imageId,
			displayOrder,
			imageType
		);
	}

	public ImageManagerId getId() {
		return internalId();
	}

}
