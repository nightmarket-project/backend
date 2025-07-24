package store.nightmarket.domain.user.service;

import static store.nightmarket.domain.user.service.dto.ChangeUserRoleDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.UserRole;

@Component
public class ChangeUserRoleDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		User user = input.getUser();
		String role = input.getRole();

		user.changeRole(UserRole.valueOf(role));

		System.out.print(user.getRole());

		return Event.builder()
			.user(user)
			.build();
	}

}
