package store.nightmarket.application.appuser.outbox;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appuser.auth.exception.OutboxException;
import store.nightmarket.application.appuser.outbox.usecase.FailOutboxUseCase;
import store.nightmarket.application.appuser.outbox.usecase.PublishOutboxUseCase;
import store.nightmarket.application.appuser.outbox.usecase.dto.FailOutboxUseCaseDto;
import store.nightmarket.application.appuser.outbox.usecase.dto.PublishOutboxUseCaseDto;
import store.nightmarket.application.appuser.outbox.util.EventTypeRegistry;
import store.nightmarket.application.appuser.outbox.util.TopicResolver;
import store.nightmarket.domain.user.model.Outbox;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final EventTypeRegistry eventTypeRegistry;
	private final TopicResolver topicResolver;
	private final PublishOutboxUseCase publishOutboxUseCase;
	private final FailOutboxUseCase failOutboxUseCase;

	@Transactional
	public void publish(Outbox outbox) {
		try {
			Object payload = deserializePayload(outbox);

			kafkaTemplate.send(
				topicResolver.resolve(outbox),
				outbox.getOutboxId().toString(),
				payload
			);

			publishOutboxUseCase.execute(
				PublishOutboxUseCaseDto.Input.builder()
					.outboxId(outbox.getOutboxId())
					.build()
			);
			log.info("[Kafka] published outboxId={}", outbox.getOutboxId());
		} catch (Exception e) {
			failOutboxUseCase.execute(
				FailOutboxUseCaseDto.Input.builder()
					.outboxId(outbox.getOutboxId())
					.build()
			);
			log.error("Failed to send Kafka. outboxId={}", outbox.getOutboxId(), e);
		}
	}

	private Object deserializePayload(Outbox outbox) {
		try {
			return objectMapper.readValue(
				outbox.getPayload(),
				eventTypeRegistry.resolve(outbox.getEventType())
			);
		} catch (Exception e) {
			throw new OutboxException("Failed to deserialize payload. type=" + outbox.getOutboxId());
		}
	}

}