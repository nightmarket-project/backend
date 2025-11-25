package store.nightmarket.application.apporder.usecase;

import static org.mockito.Mockito.*;
import static store.nightmarket.application.apporder.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.apporder.out.ReadProductVariantPort;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.out.adapter.PaymentRequestEventKafkaPublisher;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.domain.order.service.RequestOrderDomainService;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;

class RequestOrderUseCaseTest {

	private RequestOrderUseCase requestOrderUseCase;
	private SaveOrderPort mockSaveOrderPort;
	private ReadProductVariantPort mockReadProductVariantPort;
	private RequestOrderDomainService mockRequestOrderDomainService;
	private PaymentRequestEventKafkaPublisher mockPaymentRequestEventKafkaPublisher;

	@BeforeEach
	void setUp() {
		mockSaveOrderPort = mock(SaveOrderPort.class);
		mockReadProductVariantPort = mock(ReadProductVariantPort.class);
		mockRequestOrderDomainService = mock(RequestOrderDomainService.class);
		mockPaymentRequestEventKafkaPublisher = mock(PaymentRequestEventKafkaPublisher.class);

		requestOrderUseCase = new RequestOrderUseCase(
			mockSaveOrderPort,
			mockReadProductVariantPort,
			mockRequestOrderDomainService,
			mockPaymentRequestEventKafkaPublisher
		);
	}

	@Test
	@DisplayName("주문을 요청한다")
	void requestOrder() {
		// given
		RequestOrderDomainServiceDto.Event event = RequestOrderDomainServiceDto.Event.builder()
			.orderRecord(makeOrderRecord())
			.build();

		when(mockRequestOrderDomainService.execute(any(RequestOrderDomainServiceDto.Input.class))).thenReturn(event);

		RequestOrderUseCaseDto.Input usecaseInput = RequestOrderUseCaseDto.Input.builder()
			.addressDto(makeAddress())
			.userId(UUID.randomUUID())
			.detailOrderDtoList(List.of(makeDetailOrderDto()))
			.build();

		// when
		requestOrderUseCase.execute(usecaseInput);

		// then
		verify(mockSaveOrderPort, times(1))
			.save(any());
		verify(mockReadProductVariantPort, times(1))
			.readByIdList(any());
		verify(mockRequestOrderDomainService, times(1))
			.execute(any());
		verify(mockPaymentRequestEventKafkaPublisher, times(1))
			.publishEvent(any());
	}

}