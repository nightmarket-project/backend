package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@Getter
public abstract class ImageOwnerModel<IdType> extends BaseModel<IdType> {

	private final ImageOwnerId imageOwnerId;
	private final ImageOwnerType imageOwnerType;

	protected ImageOwnerModel(
		IdType id,
		ImageOwnerId imageOwnerId,
		ImageOwnerType imageOwnerType
	) {
		super(id);
		this.imageOwnerId = imageOwnerId;
		this.imageOwnerType = imageOwnerType;
	}

}
