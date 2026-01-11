package store.nightmarket.application.apppayment.usecase;

import static store.nightmarket.application.apppayment.usecase.dto.SuccessSimplePaymentUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apppayment.out.PaymentSuccessEventPublisher;
import store.nightmarket.application.apppayment.out.dto.PaymentSuccessEventDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Component
@RequiredArgsConstructor
public class SuccessSimplePaymentUseCase implements BaseUseCase<Input, Void> {

	private final PaymentSuccessEventPublisher eventPublisher;

	@Override
	public Void execute(Input input) {
		PaymentSuccessEventDto eventDto = PaymentSuccessEventDto.builder()
			.paymentRecordId(UUID.randomUUID())
			.userId(input.userId().getId())
			.orderId(input.orderId().getId())
			.price(input.price())
			.build();

		eventPublisher.publishEvent(eventDto);
		return null;
	}

}
