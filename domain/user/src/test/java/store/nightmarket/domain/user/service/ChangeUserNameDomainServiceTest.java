package store.nightmarket.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static store.nightmarket.domain.user.service.dto.ChangeUserNameDomainServiceDto.*;
import static store.nightmarket.domain.user.util.UserTestUtil.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.user.exception.UserException;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.UserRole;

class ChangeUserNameDomainServiceTest {

	private ChangeUserNameDomainService service;

	@BeforeEach
	void setUp() {
		service = new ChangeUserNameDomainService();
	}

	@Test
	@DisplayName("이름변경 요청시 해당 이름으로 변경되야 한다")
	void changeUserName() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newName = "사용자2";

		Input input = makeChangeUserNameInput(user, newName);

		// when
		Event event = service.execute(input);

		// then
		assertThat(event.getUser().getName().getValue()).isEqualTo(newName);
	}

	@Test
	@DisplayName("이름 변경 시 null로 변경할 수 없다")
	void cannotChangeUserNameNull() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newName = null;

		Input input = makeChangeUserNameInput(user, newName);

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class)
			.hasMessage("Name cannot be null");
	}

	@Test
	@DisplayName("이름 변경 시 빈칸으로 변경할 수 없다")
	void cannotChangeUserNameBlank() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newName = "";

		Input input = makeChangeUserNameInput(user, newName);

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class)
			.hasMessage("Name cannot be blank");
	}

	@Test
	@DisplayName("이름 변경 시 65자 이상으로 변경할 수 없다")
	void cannotChangeUserNameLongerThan65() {
		// given
		User user = makeUser(
			UUID.randomUUID(),
			"사용자1",
			"example@google.com",
			"1234",
			10000L,
			UserRole.BUYER
		);

		String newName = "aaaaaaaabbbbbbbbccccccccddddddddeeeeeeeeffffffffgggggggghhhhhhhhiiiiiiiiii";

		Input input = makeChangeUserNameInput(user, newName);

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(UserException.class)
			.hasMessage("Name cannot be longer than 64 characters");
	}

}