package store.nightmarket.domain.order.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;

import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.Event;
import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.Input;

public class CancelDetailOrderDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        OrderRecord orderRecord = input.getOrderRecord();
        DetailOrderRecordId detailOrderRecordId = input.getDetailOrderRecordId();

        orderRecord.cancelDetailOrder(detailOrderRecordId);

        return Event.builder()
                .orderRecord(orderRecord)
                .detailOrderRecordId(detailOrderRecordId)
                .build();
    }

}
