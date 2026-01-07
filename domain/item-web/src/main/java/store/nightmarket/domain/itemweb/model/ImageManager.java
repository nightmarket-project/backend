package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.itemweb.model.id.ImageManagerId;
import store.nightmarket.domain.itemweb.model.id.ImageOwnerId;
import store.nightmarket.domain.itemweb.model.state.ImageType;
import store.nightmarket.domain.itemweb.valueobject.Image;

@Getter
public class ImageManager extends BaseModel<ImageManagerId> {

	private final Image image;
	private final ImageType imageType;
	private final int displayOrder;
	private final ImageOwnerId ImageOwnerId;

	private ImageManager(
		ImageManagerId id,
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		super(id);
		this.image = image;
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		ImageOwnerId = imageOwnerId;
	}

	private ImageManager(
		ImageManagerId id,
		LocalDateTime createdAt,
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		super(id, createdAt);
		this.image = image;
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		ImageOwnerId = imageOwnerId;
	}

	public static ImageManager newInstance(
		ImageManagerId id,
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		return new ImageManager(
			id,
			image,
			imageType,
			displayOrder,
			imageOwnerId
		);
	}

	public static ImageManager newInstanceWithCreatedAt(
		ImageManagerId id,
		LocalDateTime createdAt,
		Image image,
		ImageType imageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		return new ImageManager(
			id,
			createdAt,
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
