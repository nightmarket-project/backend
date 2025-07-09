package store.nightmarket.domain.user.service;

import static store.nightmarket.domain.user.service.dto.ChangeUserPasswordDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.user.model.User;

@Component
public class ChangeUserPasswordDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		User user = input.getUser();
		String password = input.getPassword();

		user.changePassword(password);

		return Event.builder()
			.user(user)
			.build();
	}
	
}
