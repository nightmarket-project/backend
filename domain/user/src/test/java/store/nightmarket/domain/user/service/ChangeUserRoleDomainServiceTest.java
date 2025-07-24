package store.nightmarket.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.user.service.dto.ChangeUserRoleDomainServiceDto.*;
import static store.nightmarket.domain.user.util.UserTestUtil.*;

import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import store.nightmarket.domain.user.exception.UserException;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.AuthProvider;
import store.nightmarket.domain.user.valueobject.UserRole;

class ChangeUserRoleDomainServiceTest {

	private ChangeUserRoleDomainService service;

	@BeforeEach
	void setUp() {
		service = new ChangeUserRoleDomainService();
	}

	@Test
	@DisplayName("사용자 역할 변경 시 해당 역할로 변경되야 한다")
	void changeUserRole() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"imageUrl1@example.com",
			10000L,
			UserRole.ROLE_NONE,
			AuthProvider.GOOGLE,
			"google1"
		);

		String newRole = "ROLE_BUYER";

		Input input = makeChangeUserRoleInput(user, newRole);

		// when
		Event event = service.execute(input);

		// then
		assertThat(event.getUser().getRole().toString()).isEqualTo(newRole);
	}

	@ParameterizedTest
	@MethodSource("invalidRoles")
	@DisplayName("바뀔 수 없는 역할로 변경하고자 하면 예외를 던진다")
	void changeRoleThrowExceptionWhenInvalidRole(UserRole role) {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"imageUrl1@example.com",
			10000L,
			UserRole.ROLE_NONE,
			AuthProvider.GOOGLE,
			"google1"
		);

		String newRole = role.toString();

		Input input = makeChangeUserRoleInput(user, newRole);

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class);
	}

	private static Stream<Arguments> invalidRoles() {
		return Stream.of(
			Arguments.of(UserRole.ROLE_NONE),
			Arguments.of(UserRole.ROLE_ADMIN)
		);
	}

}