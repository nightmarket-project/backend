package store.nightmarket.domain.order.service;

import static store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto.*;
import static store.nightmarket.domain.order.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;

public class RequestOrderDomainServiceTest {

	private SoftAssertions softly;
	private RequestOrderDomainService service;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
		service = new RequestOrderDomainService();
	}

	@Test
	@DisplayName("주문을 요청하면 세부주문 전부 제출상태로 변해야 한다")
	void WhenRequestOrderThenAllDetailOrderStateChangesToSubmitted() {
		// given
		DetailOrderRecord detailOrderRecord1 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.NONE
		);
		DetailOrderRecord detailOrderRecord2 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.NONE
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord1, detailOrderRecord2));

		Input input = makeRequestInput(orderRecord);

		// when
		Event event = service.execute(input);

		// then
		OrderRecord submittedOrder = event.getOrderRecord();

		submittedOrder.getDetailOrderRecordList()
			.forEach(detail -> softly.assertThat(detail.isSubmitted()).isTrue());

		softly.assertAll();
	}

	@ParameterizedTest
	@MethodSource("invalidStates")
	@DisplayName("주문 요청 시 허용되지 않은 상태는 예외를 던진다")
	void RequestOrderThrowExceptionWhenInvalidState(DetailOrderState state) {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			state
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeRequestInput(orderRecord);

		// when & then
		softly.assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);

		softly.assertAll();
	}

	private static Stream<Arguments> invalidStates() {
		return Stream.of(
			Arguments.of(DetailOrderState.SUBMITTED),
			Arguments.of(DetailOrderState.COMPLETED),
			Arguments.of(DetailOrderState.SHIPPED),
			Arguments.of(DetailOrderState.DELIVERED),
			Arguments.of(DetailOrderState.CANCELED),
			Arguments.of(DetailOrderState.RETURNED),
			Arguments.of(DetailOrderState.REFUNDED)
		);
	}

}
