package store.nightmarket.domain.user.util;

import java.util.UUID;

import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.service.dto.ChangeUserNameDomainServiceDto;
import store.nightmarket.domain.user.service.dto.ChangeUserRoleDomainServiceDto;
import store.nightmarket.domain.user.valueobject.AuthProvider;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

public class UserTestUtil {

	public static User makeUser(
		UUID id,
		String name,
		String email,
		String imageUrl,
		Long point,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		return User.newInstance(
			new UserId(id),
			new Name(name),
			email,
			imageUrl,
			new Point(point),
			role,
			authProvider,
			providerId
		);
	}

	public static ChangeUserNameDomainServiceDto.Input makeChangeUserNameInput(User user, String name) {
		return ChangeUserNameDomainServiceDto.Input.builder()
			.user(user)
			.name(name)
			.build();
	}

	public static ChangeUserRoleDomainServiceDto.Input makeChangeUserRoleInput(User user, String role) {
		return ChangeUserRoleDomainServiceDto.Input.builder()
			.user(user)
			.role(role)
			.build();
	}

}
