package store.nightmarket.application.appuser.user.out.adapter;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appuser.user.out.UserCreatedEventPublisher;
import store.nightmarket.application.appuser.user.out.dto.UserCreatedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventKafkaPublisher implements UserCreatedEventPublisher {

	private static final String TOPIC = "user.user-created";

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishEvent(UserCreatedEvent event) {
		log.info("[Kafka] publishing UserCreatedEvent: {}", event);
		kafkaTemplate.send(TOPIC, event.userId().toString(), event);
	}

}
