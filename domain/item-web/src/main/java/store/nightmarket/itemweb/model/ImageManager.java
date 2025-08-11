package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;

@Getter
public abstract class ImageManager extends BaseModel<ImageManagerId> {

	private ImageId imageId;
	private int displayOrder;
	private final ImageType imageType;

	protected ImageManager(
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

	public ImageManagerId getId() {
		return internalId();
	}

}
