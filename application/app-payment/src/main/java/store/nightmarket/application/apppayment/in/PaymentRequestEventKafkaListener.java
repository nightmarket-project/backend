package store.nightmarket.application.apppayment.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apppayment.in.dto.PaymentRequestEvent;
import store.nightmarket.application.apppayment.usecase.SuccessSimplePaymentUseCase;
import store.nightmarket.application.apppayment.usecase.dto.SuccessSimplePaymentUseCaseDto;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.UserId;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestEventKafkaListener {

	private final SuccessSimplePaymentUseCase successSimplePaymentUseCase;

	@KafkaListener(topics = "order.payment-request", groupId = "pay-service", concurrency = "3")
	public void handleEvent(PaymentRequestEvent event) {
		log.info("order.payment-request event received: {}", event);
		successSimplePaymentUseCase.execute(
			SuccessSimplePaymentUseCaseDto.Input
				.builder()
				.orderId(new OrderId(event.orderId()))
				.userId(new UserId(event.userId()))
				.price(event.price())
				.build()
		);
	}

}
