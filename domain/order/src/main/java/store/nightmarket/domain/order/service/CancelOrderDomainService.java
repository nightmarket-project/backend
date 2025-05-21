package store.nightmarket.domain.order.service;

import static store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.order.model.OrderRecord;

@Component
public class CancelOrderDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		OrderRecord orderRecord = input.getOrderRecord();

		orderRecord.cancelAllOrder();

		return Event.builder()
			.orderRecord(orderRecord)
			.build();
	}

}
