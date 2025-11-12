package store.nightmarket.application.apporder.out.adapter;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apporder.in.dto.PaymentRequestEvent;
import store.nightmarket.application.apporder.out.PaymentRequestEventPublisher;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestEventKafkaPublisher implements PaymentRequestEventPublisher {

	private static final String TOPIC = "order.payment-request";

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishEvent(PaymentRequestEvent event) {
		log.info("[Kafka] publishing PaymentRequestEvent: {}", event);
		kafkaTemplate.send(TOPIC, event.orderId().toString(), event);
	}

}