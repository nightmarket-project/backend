package store.nightmarket.domain.user.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.user.exception.UserException;
import store.nightmarket.domain.user.valueobject.AuthProvider;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

@Getter
public class User extends BaseModel<UserId> {

	private Name name;
	private String email;
	private String imageUrl;
	private Point point;
	private UserRole role;
	private final AuthProvider authProvider;
	private final String providerId;

	private User(
		UserId id,
		Name name,
		String email,
		String imageUrl,
		Point point,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		super(id);
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
		this.point = point;
		this.role = role;
		this.authProvider = authProvider;
		this.providerId = providerId;
	}

	public static User newInstance(
		UserId id,
		Name name,
		String email,
		String imageUrl,
		Point point,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		return new User(
			id,
			name,
			email,
			imageUrl,
			point,
			role,
			authProvider,
			providerId
		);
	}

	public UserId getUserId() {
		return internalId();
	}

	public void changeName(String name) {
		this.name = new Name(name);
	}

	public void changeRole(UserRole role) {
		if (!this.role.canTransitionTo(role)) {
			throw new UserException("cannot change role to " + role);
		}
		this.role = role;
	}

}
