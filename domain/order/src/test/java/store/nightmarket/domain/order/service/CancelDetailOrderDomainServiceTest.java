package store.nightmarket.domain.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.Event;
import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.Input;

public class CancelDetailOrderDomainServiceTest {

    @Test
    @DisplayName("세부주문을 취소하면 해당 세부주문 만 취소상태로 변해야 한다")
    void WhenCancelDetailOrderThenDetailOrderStateChangesToCanceled() {
        // given
        DetailOrderRecordId id1 = new DetailOrderRecordId(UUID.randomUUID());
        DetailOrderRecordId id2 = new DetailOrderRecordId(UUID.randomUUID());
        OrderRecord orderRecord = orderRecord(id1, id2);
        Input input = Input.builder().
                            orderRecord(orderRecord).
                            detailOrderRecordId(id1).
                            build();

        CancelDetailOrderDomainService service = new CancelDetailOrderDomainService();

        // when
        Event event = service.execute(input);
        OrderRecord canceledOrder = event.getOrderRecord();
        DetailOrderRecord detailOrderRecord1 = canceledOrder.getDetailOrderRecordList().getFirst();
        DetailOrderRecord detailOrderRecord2 = canceledOrder.getDetailOrderRecordList().getLast();

        // then
        assertTrue(detailOrderRecord1.isCanceled());
        assertFalse(detailOrderRecord2.isCanceled());
    }

    private DetailOrderRecord detailOrderRecord1(DetailOrderRecordId id) {
        return DetailOrderRecord.newInstance(
                id,
                new ProductId(UUID.randomUUID()),
                new Quantity(1),
                DetailOrderState.SUBMITTED
        );
    }

    private DetailOrderRecord detailOrderRecord2(DetailOrderRecordId id) {
        return DetailOrderRecord.newInstance(
                id,
                new ProductId(UUID.randomUUID()),
                new Quantity(1),
                DetailOrderState.SUBMITTED
        );
    }

    private OrderRecord orderRecord(DetailOrderRecordId id1, DetailOrderRecordId id2) {
        return OrderRecord.newInstance(
                new OrderRecordId(UUID.randomUUID()),
                List.of(detailOrderRecord1(id1), detailOrderRecord2(id2)),
                new Address("111111","하늘로 111-111","구름아파트 111동 111호"),
                LocalDate.of(2025,4,29),
                new UserId(UUID.randomUUID())
        );
    }

    private Input input(OrderRecord orderRecord) {
        return Input.builder()
                .orderRecord(orderRecord)
                .build();
    }

}
