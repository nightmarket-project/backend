package store.nightmarket.itemweb.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@Getter
public abstract class ImageOwnerModel<IdType> extends BaseModel<IdType> {

	private final ImageOwnerId imageOwnerId;
	private final ImageOwnerType imageOwnerType;
	private List<ImageManager> imageManagerList;

	protected ImageOwnerModel(
		IdType id,
		ImageOwnerId imageOwnerId,
		ImageOwnerType imageOwnerType,
		List<ImageManager> imageManagerList
	) {
		super(id);
		this.imageOwnerId = imageOwnerId;
		this.imageOwnerType = imageOwnerType;
		this.imageManagerList = new ArrayList<>(
			imageManagerList != null ? imageManagerList : new ArrayList<>());
	}

	protected void changeImageManager(List<ImageManager> imageManagerList) {
		this.imageManagerList = new ArrayList<>(imageManagerList);
	}

}
