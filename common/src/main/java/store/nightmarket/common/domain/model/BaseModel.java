package store.nightmarket.common.domain.model;

public abstract class BaseModel<IdType> {

	private IdType id;

	public BaseModel() {
	}

	public BaseModel(IdType id) {
		this.id = id;
	}

	protected IdType internalId() {
		return id;
	}

}
