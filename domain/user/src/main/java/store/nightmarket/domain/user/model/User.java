package store.nightmarket.domain.user.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.model.id.UserId;

@Getter
public class User extends BaseModel<UserId> {

	private Name name;
	private String email;
	private String imageUrl;
	private Point point;
	private UserRole role;
	private AuthProvider authProvider;
	private String providerId;

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

	private User(
		UserId id,
		LocalDateTime createdAt,
		Name name,
		String email,
		String imageUrl,
		Point point,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		super(id, createdAt);
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

	public static User newInstanceWithCreatedAt(
		UserId id,
		LocalDateTime createdAt,
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
			createdAt,
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
		this.role = role;
	}

}
