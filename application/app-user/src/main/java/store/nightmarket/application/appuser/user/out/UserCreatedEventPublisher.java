package store.nightmarket.application.appuser.user.out;

import store.nightmarket.application.appuser.user.out.dto.UserCreatedEvent;

public interface UserCreatedEventPublisher {

	void publishEvent(UserCreatedEvent event);

}
