package store.nightmarket.domain.order.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.order.model.OrderRecord;

import static store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto.*;

public class RequestOrderDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        OrderRecord orderRecord = input.getOrderRecord();

        orderRecord.requestOrder();

        return Event.builder()
                .orderRecord(orderRecord)
                .build();
    }

}
