package store.nightmarket.application.apporder.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static store.nightmarket.application.apporder.out.feign.PreemptApiDto.*;
import static store.nightmarket.application.apporder.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import feign.FeignException;
import store.nightmarket.application.apporder.out.ReadProductVariantPort;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.out.adapter.PaymentRequestEventKafkaPublisher;
import store.nightmarket.application.apporder.out.feign.PreemptApiCaller;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.service.RequestOrderDomainService;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;

class RequestOrderUseCaseTest {

	private RequestOrderUseCase requestOrderUseCase;
	private SaveOrderPort mockSaveOrderPort;
	private ReadProductVariantPort mockReadProductVariantPort;
	private RequestOrderDomainService mockRequestOrderDomainService;
	private PreemptApiCaller mockPreemptApiCaller;
	private PaymentRequestEventKafkaPublisher mockPaymentRequestEventKafkaPublisher;

	@BeforeEach
	void setUp() {
		mockSaveOrderPort = mock(SaveOrderPort.class);
		mockReadProductVariantPort = mock(ReadProductVariantPort.class);
		mockRequestOrderDomainService = mock(RequestOrderDomainService.class);
		mockPreemptApiCaller = mock(PreemptApiCaller.class);
		mockPaymentRequestEventKafkaPublisher = mock(PaymentRequestEventKafkaPublisher.class);

		requestOrderUseCase = new RequestOrderUseCase(
			mockSaveOrderPort,
			mockReadProductVariantPort,
			mockRequestOrderDomainService,
			mockPreemptApiCaller,
			mockPaymentRequestEventKafkaPublisher
		);
	}

	@Test
	@DisplayName("재고가 충분할 경우 주문이 요청된다")
	void shouldRequestOrderWhenStockEnough() {
		// given
		RequestOrderDomainServiceDto.Event event = RequestOrderDomainServiceDto.Event.builder()
			.orderRecord(makeOrderRecord())
			.build();

		when(mockRequestOrderDomainService.execute(any(RequestOrderDomainServiceDto.Input.class)))
			.thenReturn(event);

		RequestOrderUseCaseDto.Input usecaseInput = RequestOrderUseCaseDto.Input.builder()
			.addressDto(makeAddress())
			.userId(UUID.randomUUID())
			.detailOrderDtoList(List.of(makeDetailOrderDto()))
			.build();

		PreemptResponse response = PreemptResponse.builder()
			.isSuccess(true)
			.insufficientProductList(List.of())
			.build();

		when(mockPreemptApiCaller.preemptRequest(any(PreemptRequest.class)))
			.thenReturn(response);

		// when
		requestOrderUseCase.execute(usecaseInput);

		// then
		verify(mockSaveOrderPort, times(1))
			.save(any());
		verify(mockReadProductVariantPort, times(1))
			.readByIdList(any());
		verify(mockRequestOrderDomainService, times(1))
			.execute(any());
		verify(mockPreemptApiCaller, times(1))
			.preemptRequest(any());
		verify(mockPaymentRequestEventKafkaPublisher, times(1))
			.publishEvent(any());
	}

	@Test
	@DisplayName("재고가 부족할 경우 결제요청을 보내지 않는다")
	void shouldNotPaymentRequestWhenOutOfStock() {
		// given
		RequestOrderDomainServiceDto.Event event = RequestOrderDomainServiceDto.Event.builder()
			.orderRecord(makeOrderRecord())
			.build();

		when(mockRequestOrderDomainService.execute(any(RequestOrderDomainServiceDto.Input.class)))
			.thenReturn(event);

		RequestOrderUseCaseDto.Input usecaseInput = RequestOrderUseCaseDto.Input.builder()
			.addressDto(makeAddress())
			.userId(UUID.randomUUID())
			.detailOrderDtoList(List.of(makeDetailOrderDto()))
			.build();

		PreemptResponse response = PreemptResponse.builder()
			.isSuccess(false)
			.insufficientProductList(List.of(UUID.randomUUID()))
			.build();

		when(mockPreemptApiCaller.preemptRequest(any(PreemptRequest.class)))
			.thenReturn(response);

		// when
		requestOrderUseCase.execute(usecaseInput);

		// then
		verify(mockSaveOrderPort, times(1))
			.save(any());
		verify(mockReadProductVariantPort, times(0))
			.readByIdList(any());
		verify(mockRequestOrderDomainService, times(1))
			.execute(any());
		verify(mockPreemptApiCaller, times(1))
			.preemptRequest(any());
		verify(mockPaymentRequestEventKafkaPublisher, times(0))
			.publishEvent(any());
	}

	@Test
	@DisplayName("선점요청 오류시 예외를 던진다")
	void shouldThrowExceptionWhenPreemptApiCallFail() {
		// given
		RequestOrderDomainServiceDto.Event event = RequestOrderDomainServiceDto.Event.builder()
			.orderRecord(makeOrderRecord())
			.build();

		when(mockRequestOrderDomainService.execute(any(RequestOrderDomainServiceDto.Input.class)))
			.thenReturn(event);

		RequestOrderUseCaseDto.Input usecaseInput = RequestOrderUseCaseDto.Input.builder()
			.addressDto(makeAddress())
			.userId(UUID.randomUUID())
			.detailOrderDtoList(List.of(makeDetailOrderDto()))
			.build();

		when(mockPreemptApiCaller.preemptRequest(any(PreemptRequest.class)))
			.thenThrow(FeignException.class);

		// when & then
		assertThatThrownBy(() -> requestOrderUseCase.execute(usecaseInput))
			.isInstanceOf(OrderException.class);
	}

}