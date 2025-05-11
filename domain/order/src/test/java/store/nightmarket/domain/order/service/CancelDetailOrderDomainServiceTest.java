package store.nightmarket.domain.order.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.*;
import static store.nightmarket.domain.order.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;

public class CancelDetailOrderDomainServiceTest {

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

		CancelDetailOrderDomainService service = new CancelDetailOrderDomainService();

		// when
		Event event = service.execute(input);

		// then
		OrderRecord canceledOrder = event.getOrderRecord();

		DetailOrderRecord canceledRecord1 = canceledOrder.getDetailOrderRecordList().getFirst();
		DetailOrderRecord canceledRecord2 = canceledOrder.getDetailOrderRecordList().getLast();

		assertThat(canceledRecord1.isCanceled()).isTrue();
		assertThat(canceledRecord2.isCanceled()).isFalse();
	}

	@Test
	@DisplayName("이미 취소된 세부주문은 다시 취소 할 수 없다")
	void throwExceptionIfDetailOrderAlreadyCanceled() {
		// given
		DetailOrderRecord detailOrderRecord1 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.CANCELED
		);
		DetailOrderRecord detailOrderRecord2 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord1, detailOrderRecord2));

		Input input = makeDetailCancelInput(orderRecord, detailOrderRecord1);

		CancelDetailOrderDomainService service = new CancelDetailOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

	@Test
	@DisplayName("이미 제출된 세부주문은 취소 할 수 없다")
	void throwExceptionIfDetailOrderAlreadySubmitted() {
		// given
		DetailOrderRecord detailOrderRecord1 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.SUBMITTED
		);
		DetailOrderRecord detailOrderRecord2 = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord1, detailOrderRecord2));

		Input input = makeDetailCancelInput(orderRecord, detailOrderRecord1);

		CancelDetailOrderDomainService service = new CancelDetailOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

}
