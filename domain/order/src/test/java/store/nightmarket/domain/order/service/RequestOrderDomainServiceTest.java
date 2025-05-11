package store.nightmarket.domain.order.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto.*;
import static store.nightmarket.domain.order.util.OrderTestUtil.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;

public class RequestOrderDomainServiceTest {

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

		RequestOrderDomainService service = new RequestOrderDomainService();

		// when
		Event event = service.execute(input);

		// then
		OrderRecord submittedOrder = event.getOrderRecord();

		submittedOrder.getDetailOrderRecordList()
			.forEach(detail -> assertThat(detail.isSubmitted()).isTrue());
	}

	@Test
	@DisplayName("이미 제출된 주문은 다시 제출 할 수 없다")
	void throwExceptionIfOrderAlreadySubmitted() {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.SUBMITTED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeRequestInput(orderRecord);

		RequestOrderDomainService service = new RequestOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

	@Test
	@DisplayName("이미 완료된 주문은 제출 할 수 없다")
	void throwExceptionIfOrderAlreadyCompleted() {
		// given
		DetailOrderRecord detailOrderRecord = makeDetailOrderRecord(
			UUID.randomUUID(),
			UUID.randomUUID(),
			1,
			DetailOrderState.COMPLETED
		);

		OrderRecord orderRecord = makeOrderRecord(List.of(detailOrderRecord));

		Input input = makeRequestInput(orderRecord);

		RequestOrderDomainService service = new RequestOrderDomainService();

		// when & then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(OrderException.class);
	}

}
