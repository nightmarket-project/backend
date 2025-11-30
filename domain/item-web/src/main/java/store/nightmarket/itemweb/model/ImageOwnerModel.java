package store.nightmarket.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.model.state.ImageOwnerType;
import store.nightmarket.itemweb.model.id.ImageOwnerId;

@Getter
public abstract class ImageOwnerModel<IdType extends ImageOwnerId> extends BaseModel<IdType> {

	private final ImageOwnerType imageOwnerType;

	protected ImageOwnerModel(
		IdType id,
		ImageOwnerType imageOwnerType
	) {
		super(id);
		this.imageOwnerType = imageOwnerType;
	}

	protected ImageOwnerModel(
		IdType id,
		LocalDateTime createdAt,
		ImageOwnerType imageOwnerType
	) {
		super(id, createdAt);
		this.imageOwnerType = imageOwnerType;
	}

}
