package store.nightmarket.application.apppayment.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apppayment.in.dto.PaymentRequestEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestEventKafkaListener {

	@KafkaListener(topics = "order.payment-request", groupId = "pay-service", concurrency = "3")
	public void handleEvent(PaymentRequestEvent event) {
		log.info("order.payment-request event received: {}", event);
	}

}
