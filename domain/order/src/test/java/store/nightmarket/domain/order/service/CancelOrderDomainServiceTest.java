package store.nightmarket.domain.order.service;

import static store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto.*;
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

public class CancelOrderDomainServiceTest {

	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("주문을 취소하면 세부주문 전부 취소상태로 변해야 한다")
	void WhenCancelOrderThenAllDetailOrderStateChangesToCanceled() {
		// given
		DetailOrderRecord detailOrderRecord1 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);
		DetailOrderRecord detailOrderRecord2 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord1, detailOrderRecord2));

		Input input = makeCancelinput(orderRecord);

		CancelOrderDomainService service = new CancelOrderDomainService();

		// when
		Event event = service.execute(input);

		// then
		OrderRecord canceledOrder = event.getOrderRecord();

		canceledOrder.getDetailOrderRecordList()
			.forEach(detail -> softly.assertThat(detail.isCanceled()).isTrue());

		softly.assertAll();
	}

	@ParameterizedTest
	@MethodSource("invalidStates")
	@DisplayName("주문 취소 시 허용되지 않은 상태는 예외를 던진다")
	void CancelOrderThrowExceptionWhenInvalidState(DetailOrderState state) {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			state
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeCancelinput(orderRecord);

		CancelOrderDomainService service = new CancelOrderDomainService();

		// when & then
		softly.assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);

		softly.assertAll();
	}

	private static Stream<Arguments> invalidStates() {
		return Stream.of(
			Arguments.of(DetailOrderState.SUBMITTED),
			Arguments.of(DetailOrderState.SHIPPED),
			Arguments.of(DetailOrderState.DELIVERED),
			Arguments.of(DetailOrderState.CANCELED),
			Arguments.of(DetailOrderState.RETURNED),
			Arguments.of(DetailOrderState.REFUNDED)
		);
	}

}
