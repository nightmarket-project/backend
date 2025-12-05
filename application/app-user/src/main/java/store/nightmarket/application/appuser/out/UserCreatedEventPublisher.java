package store.nightmarket.application.appuser.out;

import store.nightmarket.application.appuser.out.dto.UserCreatedEvent;

public interface UserCreatedEventPublisher {

	void publishEvent(UserCreatedEvent event);

}
