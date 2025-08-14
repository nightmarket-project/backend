package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

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

}
