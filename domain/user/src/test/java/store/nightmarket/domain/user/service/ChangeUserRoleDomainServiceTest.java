package store.nightmarket.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.user.service.dto.ChangeUserRoleDomainServiceDto.*;
import static store.nightmarket.domain.user.util.UserTestUtil.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.model.AuthProvider;
import store.nightmarket.domain.user.model.UserRole;

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
			UserRole.ROLE_BUYER,
			AuthProvider.GOOGLE,
			"google1"
		);

		String newRole = "ROLE_SELLER";

		Input input = makeChangeUserRoleInput(user, newRole);

		// when
		Event event = service.execute(input);

		// then
		assertThat(event.getUser().getRole().toString()).isEqualTo(newRole);
	}

}