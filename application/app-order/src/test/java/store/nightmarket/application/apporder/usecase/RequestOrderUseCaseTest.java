package store.nightmarket.application.apporder.usecase;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.service.RequestOrderDomainService;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductId;
import store.nightmarket.domain.order.valueobject.Quantity;
import store.nightmarket.domain.order.valueobject.UserId;

class RequestOrderUseCaseTest {

	private RequestOrderUseCase requestOrderUseCase;
	private SaveOrderPort mockSaveOrderPort;
	private RequestOrderDomainService mockRequestOrderDomainService;

	@BeforeEach
	void setUp() {
		mockSaveOrderPort = mock(SaveOrderPort.class);
		mockRequestOrderDomainService = mock(RequestOrderDomainService.class);
		requestOrderUseCase = new RequestOrderUseCase(
			mockSaveOrderPort,
			mockRequestOrderDomainService
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
			.build();

		// when
		requestOrderUseCase.execute(usecaseInput);

		// then
		verify(mockSaveOrderPort, times(1))
			.save(any());
		verify(mockRequestOrderDomainService, times(1))
			.execute(any());
	}

	@Test
	@DisplayName("")
	void test() {
		// given
		// when
		// then
	}

	private RequestOrderUseCaseDto.AddressDto makeAddress() {
		return RequestOrderUseCaseDto.AddressDto.builder()
			.zipCode("111-111")
			.roadAddress("구름로 111-111")
			.detailAddress("111동 111호")
			.build();
	}

	private OrderRecord makeOrderRecord() {
		return OrderRecord.newInstance(
			new OrderRecordId(UUID.randomUUID()),
			new Address(
				"111-111",
				"구름로 111-111",
				"111동 111호"
			),
			LocalDate.now(),
			new UserId(UUID.randomUUID()),
			new ArrayList<>(List.of(makeDetailOrderRecord()))
		);
	}

	private DetailOrderRecord makeDetailOrderRecord() {
		return DetailOrderRecord.newInstance(
			new DetailOrderRecordId(UUID.randomUUID()),
			new ProductId(UUID.randomUUID()),
			new Quantity(1),
			DetailOrderState.SUBMITTED
		);
	}

}