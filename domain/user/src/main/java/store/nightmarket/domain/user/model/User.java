package store.nightmarket.domain.user.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

public class User extends BaseModel<UserId> {

	private Name name;
	private Point point;
	private UserRole role;

	public User(
		UserId id,
		Name name,
		Point point,
		UserRole role
	) {
		super(id);
		this.name = name;
		this.point = point;
		this.role = role;
	}

	public User newInstance(
		UserId id,
		Name name,
		Point point,
		UserRole role
	) {
		return new User(
			id,
			name,
			point,
			role
		);
	}

}
