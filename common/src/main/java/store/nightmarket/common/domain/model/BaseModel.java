package store.nightmarket.common.domain.model;

public abstract class BaseModel<IdType> {

	private final BaseId<IdType> id;

	public BaseModel(BaseId<IdType> id) {
		this.id = id;
	}

}
