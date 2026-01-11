package store.nightmarket.application.apppayment.out.adapter;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apppayment.out.PaymentSuccessEventPublisher;
import store.nightmarket.application.apppayment.out.dto.PaymentSuccessEventDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessEventKafkaPublisher implements PaymentSuccessEventPublisher {

	private static final String TOPIC = "order.payment-result";

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishEvent(PaymentSuccessEventDto event) {
		log.info("[Kafka] publishing PaymentRequestEvent: {}", event);
		kafkaTemplate.send(TOPIC, event.paymentRecordId().toString(), event);
	}

}
