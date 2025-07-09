package store.nightmarket.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static store.nightmarket.domain.user.service.dto.ChangeUserPasswordDomainServiceDto.*;
import static store.nightmarket.domain.user.util.UserTestUtil.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.user.exception.UserException;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.UserRole;

class ChangeUserPasswordDomainServiceTest {

	ChangeUserPasswordDomainService service;

	@BeforeEach
	void setUp() {
		service = new ChangeUserPasswordDomainService();
	}

	@Test
	@DisplayName("비밀번호 변경 시 해당 비밀번호로 변경 되어야 한다")
	void changeUserPassword() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newPassword = "5678";

		Input input = makeChangeUserPasswordInput(user, newPassword);

		// when
		Event event = service.execute(input);

		// then
		assertThat(event.getUser().getPassword().getValue()).isEqualTo(newPassword);
	}

	@Test
	@DisplayName("비밀번호 변경 시 null로 변경할 수 없다")
	void cannotChangeUserPasswordNull() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newPassword = null;

		Input input = makeChangeUserPasswordInput(user, newPassword);

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class)
			.hasMessage("Password cannot be null");
	}

	@Test
	@DisplayName("비밀번호 변경 시 빈칸으로 변경할 수 없다")
	void cannotChangeUserPasswordBlank() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newPassword = "";

		Input input = makeChangeUserPasswordInput(user, newPassword);

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class)
			.hasMessage("Password cannot be blank");
	}

}
