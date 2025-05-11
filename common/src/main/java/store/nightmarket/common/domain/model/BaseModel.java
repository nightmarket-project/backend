package store.nightmarket.common.domain.model;

import lombok.Getter;

@Getter
public abstract class BaseModel<IdType> {

	private final IdType id;

	public BaseModel(IdType id) {
		this.id = id;
	}

}
