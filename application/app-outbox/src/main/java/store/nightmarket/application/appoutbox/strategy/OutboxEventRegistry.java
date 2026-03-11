package store.nightmarket.application.appoutbox.strategy;

import java.util.Set;

public interface OutboxEventRegistry {
	String resolveTopic(String eventType);

	Class<?> resolvePayloadType(String eventType);

	Set<String> getSupportedEventTypes();
}