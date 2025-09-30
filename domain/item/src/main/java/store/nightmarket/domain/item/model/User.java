package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.UserId;

@Getter
public class User extends BaseModel<UserId> {

	private Name name;

	private User(
		UserId id,
		Name name
	) {
		super(id);
		this.name = name;
	}

	private User(
		UserId id,
		LocalDateTime createdAt,
		Name name
	) {
		super(id, createdAt);
		this.name = name;
	}

	public static User newInstance(
		UserId id,
		Name name
	) {
		return new User(
			id,
			name
		);
	}

	public static User newInstanceWithCreatedAt(
		UserId id,
		LocalDateTime createdAt,
		Name name
	) {
		return new User(
			id,
			createdAt,
			name
		);
	}

	public UserId getUserId() {
		return internalId();
	}

}
