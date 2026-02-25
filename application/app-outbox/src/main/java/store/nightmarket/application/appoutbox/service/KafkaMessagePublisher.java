package store.nightmarket.application.appoutbox.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appoutbox.strategy.OutboxEventRegistry;
import store.nightmarket.domain.outbox.model.Outbox;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final OutboxEventRegistry outboxEventRegistry;

	public boolean publish(Outbox outbox) {
		try {
			kafkaTemplate.send(
				outboxEventRegistry.resolveTopic(outbox.getEventType()),
				outbox.getOutboxId().toString(),
				deserializePayload(outbox)
			);
			log.info("[Kafka] success to publish outboxId={}", outbox.getOutboxId());
			return true;
		} catch (Exception e) {
			log.error("[Kafka] Failed to publish outboxId={}", outbox.getOutboxId(), e);
			return false;
		}
	}

	private Object deserializePayload(Outbox outbox) throws JsonProcessingException {
		return objectMapper.readValue(
			outbox.getPayload(),
			outboxEventRegistry.resolvePayloadType(outbox.getEventType())
		);
	}

}