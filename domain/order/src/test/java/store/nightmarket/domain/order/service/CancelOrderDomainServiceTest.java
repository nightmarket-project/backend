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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto.Event;
import static store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto.Input;

public class CancelOrderDomainServiceTest {

    @Test
    @DisplayName("주문을 취소하면 세부주문 전부 취소상태로 변해야 한다")
    void WhenCancelOrderThenAllDetailOrderStateChangesToCanceled() {
        // given
        OrderRecord orderRecord = orderRecord();
        Input input = Input.builder().
                            orderRecord(orderRecord).
                            build();

        CancelOrderDomainService service = new CancelOrderDomainService();

        // when
        Event event = service.execute(input);
        OrderRecord canceledOrder = event.getOrderRecord();

        // then
        canceledOrder.getDetailOrderRecordList()
                .forEach(detail -> assertTrue(detail.isCanceled()));
    }

    private DetailOrderRecord detailOrderRecord1() {
        return DetailOrderRecord.newInstance(
                new DetailOrderRecordId(UUID.randomUUID()),
                new ProductId(UUID.randomUUID()),
                new Quantity(1),
                DetailOrderState.SUBMITTED
        );
    }

    private DetailOrderRecord detailOrderRecord2() {
        return DetailOrderRecord.newInstance(
                new DetailOrderRecordId(UUID.randomUUID()),
                new ProductId(UUID.randomUUID()),
                new Quantity(1),
                DetailOrderState.SUBMITTED
        );
    }

    private OrderRecord orderRecord() {
        return OrderRecord.newInstance(
                new OrderRecordId(UUID.randomUUID()),
                List.of(detailOrderRecord1(), detailOrderRecord2()),
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
