package store.nightmarket.application.apppayment.usecase;

import static org.mockito.Mockito.*;
import static store.nightmarket.application.apppayment.usecase.dto.SuccessSimplePaymentUseCaseDto.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.apppayment.out.PaymentSuccessEventPublisher;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.UserId;

class SuccessSimplePaymentUseCaseTest {

	private PaymentSuccessEventPublisher mockEventPublisher;
	private SuccessSimplePaymentUseCase successSimplePaymentUseCase;

	@BeforeEach
	void setUp() {
		mockEventPublisher = mock(PaymentSuccessEventPublisher.class);
		successSimplePaymentUseCase = new SuccessSimplePaymentUseCase(mockEventPublisher);
	}

	@Test
	@DisplayName("결제성공을 요청한다")
	void shouldReturnPaymentRecordWhenRequestPayment() {
		// given
		Input input = Input.builder()
			.userId(new UserId(UUID.randomUUID()))
			.orderId(new OrderId(UUID.randomUUID()))
			.price(10000L)
			.build();

		// when
		successSimplePaymentUseCase.execute(input);

		// then
		verify(mockEventPublisher, times(1))
			.publishEvent(any());
	}

}
