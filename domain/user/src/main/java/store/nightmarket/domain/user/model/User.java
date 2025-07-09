package store.nightmarket.domain.user.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.user.valueobject.Account;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Password;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

@Getter
public class User extends BaseModel<UserId> {

	private Name name;
	private Account account;
	private Password password;
	private Point point;
	private final UserRole role;

	private User(
		UserId id,
		Name name,
		Account account,
		Password password,
		Point point,
		UserRole role
	) {
		super(id);
		this.name = name;
		this.account = account;
		this.password = password;
		this.point = point;
		this.role = role;
	}

	public static User newInstance(
		UserId id,
		Name name,
		Account account,
		Password password,
		Point point,
		UserRole role
	) {
		return new User(
			id,
			name,
			account,
			password,
			point,
			role
		);
	}

	public void changeName(String name) {
		this.name = new Name(name);
	}

	public void changePassword(String password) {
		this.password = new Password(password);
	}

}
