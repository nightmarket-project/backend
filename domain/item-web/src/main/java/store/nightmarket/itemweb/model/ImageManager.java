package store.nightmarket.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@Getter
public class ImageManager extends BaseModel<ImageManagerId> {

	private final Image image;
	private final DomainImageType domainImageType;
	private final int displayOrder;
	private final ImageOwnerId ImageOwnerId;

	private ImageManager(
		ImageManagerId id,
		Image image,
		DomainImageType domainImageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		super(id);
		this.image = image;
		this.domainImageType = domainImageType;
		this.displayOrder = displayOrder;
		ImageOwnerId = imageOwnerId;
	}

	private ImageManager(
		ImageManagerId id,
		LocalDateTime createdAt,
		Image image,
		DomainImageType domainImageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		super(id, createdAt);
		this.image = image;
		this.domainImageType = domainImageType;
		this.displayOrder = displayOrder;
		ImageOwnerId = imageOwnerId;
	}

	public static ImageManager newInstance(
		ImageManagerId id,
		Image image,
		DomainImageType domainImageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		return new ImageManager(
			id,
			image,
			domainImageType,
			displayOrder,
			imageOwnerId
		);
	}

	public static ImageManager newInstanceWithCreatedAt(
		ImageManagerId id,
		LocalDateTime createdAt,
		Image image,
		DomainImageType domainImageType,
		int displayOrder,
		ImageOwnerId imageOwnerId
	) {
		return new ImageManager(
			id,
			createdAt,
			image,
			domainImageType,
			displayOrder,
			imageOwnerId
		);
	}

	public ImageManagerId getImageManagerId() {
		return internalId();
	}

}
