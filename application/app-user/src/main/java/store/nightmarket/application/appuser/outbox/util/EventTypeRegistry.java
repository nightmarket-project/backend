package store.nightmarket.application.appuser.outbox.util;

import static store.nightmarket.application.appuser.constant.Constant.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import store.nightmarket.application.appuser.auth.exception.OutboxException;
import store.nightmarket.application.appuser.out.dto.UserCreatedEvent;

@Component
public class EventTypeRegistry {

	private final Map<String, Class<?>> mapping = new HashMap<>();

	public EventTypeRegistry() {
		mapping.put(USER_CREATED_EVENT, UserCreatedEvent.class);
	}

	public Class<?> resolve(String eventType) {
		Class<?> clazz = mapping.get(eventType);
		if (clazz == null) {
			throw new OutboxException("Unknown eventType: " + eventType);
		}
		return clazz;
	}

}
