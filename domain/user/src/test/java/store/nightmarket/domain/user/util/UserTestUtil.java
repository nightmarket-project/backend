package store.nightmarket.domain.user.util;

import java.util.UUID;

import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.service.dto.ChangeUserNameDomainServiceDto;
import store.nightmarket.domain.user.service.dto.ChangeUserPasswordDomainServiceDto;
import store.nightmarket.domain.user.valueobject.Account;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Password;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

public class UserTestUtil {

	public static User makeUser(
		UUID userId,
		String name,
		String account,
		String password,
		Long point,
		UserRole role
	) {
		return User.newInstance(
			new UserId(userId),
			new Name(name),
			new Account(account),
			new Password(password),
			new Point(point),
			role
		);
	}

	public static ChangeUserNameDomainServiceDto.Input makeChangeUserNameInput(User user, String name) {
		return ChangeUserNameDomainServiceDto.Input.builder()
			.user(user)
			.name(name)
			.build();
	}

	public static ChangeUserPasswordDomainServiceDto.Input makeChangeUserPasswordInput(User user, String password) {
		return ChangeUserPasswordDomainServiceDto.Input.builder()
			.user(user)
			.password(password)
			.build();
	}

}
