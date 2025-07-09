package store.nightmarket.domain.user.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.user.model.User;

public class ChangeUserPasswordDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private final User user;
		private final String password;

	}

	@Getter
	@Builder
	public static class Event {

		private final User user;

	}

}
