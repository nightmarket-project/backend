package store.nightmarket.application.appoutbox.strategy;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import store.nightmarket.domain.outbox.exception.OutboxException;

@RequiredArgsConstructor
public class DefaultOutboxEventRegistry implements OutboxEventRegistry {

	private final Map<String, String> topicMap;
	private final Map<String, Class<?>> payloadTypeMap;

	@Override
	public String resolveTopic(String eventType) {
		String topic = topicMap.get(eventType);
		if (topic == null)
			throw new OutboxException("Unknown topic for eventType: " + eventType);
		return topic;
	}

	@Override
	public Class<?> resolvePayloadType(String eventType) {
		Class<?> type = payloadTypeMap.get(eventType);
		if (type == null)
			throw new OutboxException("Unknown payloadType for eventType: " + eventType);
		return type;
	}

}