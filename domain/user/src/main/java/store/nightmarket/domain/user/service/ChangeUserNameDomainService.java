package store.nightmarket.domain.user.service;

import static store.nightmarket.domain.user.service.dto.ChangeUserNameDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.user.model.User;

@Component
public class ChangeUserNameDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		User user = input.getUser();
		String name = input.getName();

		user.changeName(name);

		return Event.builder()
			.user(user)
			.build();
	}

}
