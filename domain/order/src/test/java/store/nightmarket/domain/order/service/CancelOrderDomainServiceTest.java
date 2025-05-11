package store.nightmarket.domain.order.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto.*;
import static store.nightmarket.domain.order.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;

public class CancelOrderDomainServiceTest {

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
			.forEach(detail -> assertThat(detail.isCanceled()).isTrue());
	}

	@Test
	@DisplayName("이미 취소된 주문은 다시 취소 할 수 없다")
	void throwExceptionIfOrderAlreadyCanceled() {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.CANCELED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeCancelinput(orderRecord);

		CancelOrderDomainService service = new CancelOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

	@Test
	@DisplayName("이미 제출된 주문은 취소 할 수 없다")
	void throwExceptionIfOrderAlreadySubmitted() {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.SUBMITTED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeCancelinput(orderRecord);

		CancelOrderDomainService service = new CancelOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

}
