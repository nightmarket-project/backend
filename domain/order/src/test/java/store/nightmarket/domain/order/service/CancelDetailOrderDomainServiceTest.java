package store.nightmarket.domain.order.service;

import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.*;
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

public class CancelDetailOrderDomainServiceTest {

	private SoftAssertions softly;
	private CancelDetailOrderDomainService service;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
		service = new CancelDetailOrderDomainService();
	}

	@Test
	@DisplayName("세부주문을 취소하면 해당 세부주문 만 취소상태로 변해야 한다")
	void WhenCancelDetailOrderThenDetailOrderStateChangesToCanceled() {
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

		Input input = makeDetailCancelInput(orderRecord, detailOrderRecord1);

		// when
		Event event = service.execute(input);

		// then
		OrderRecord canceledOrder = event.getOrderRecord();

		DetailOrderRecord canceledRecord1 = canceledOrder.getDetailOrderRecordList().getFirst();
		DetailOrderRecord canceledRecord2 = canceledOrder.getDetailOrderRecordList().getLast();

		softly.assertThat(canceledRecord1.isCanceled()).isTrue();
		softly.assertThat(canceledRecord2.isCanceled()).isFalse();

		softly.assertAll();
	}

	@ParameterizedTest
	@MethodSource("invalidStates")
	@DisplayName("세부 주문 취소 시 허용되지 않은 상태는 예외를 던진다")
	void CancelDetailOrderThrowExceptionWhenInvalidState(DetailOrderState state) {
		// given
		DetailOrderRecord detailOrderRecord1 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			state
		);
		DetailOrderRecord detailOrderRecord2 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord1, detailOrderRecord2));

		Input input = makeDetailCancelInput(orderRecord, detailOrderRecord1);
		
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
