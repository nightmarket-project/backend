package store.nightmarket.application.appoutbox.strategy;

public interface OutboxEventRegistry {
	String resolveTopic(String eventType);

	Class<?> resolvePayloadType(String eventType);
}