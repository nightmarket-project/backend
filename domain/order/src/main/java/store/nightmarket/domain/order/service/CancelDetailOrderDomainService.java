package store.nightmarket.domain.order.service;

import static store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;

@Component
public class CancelDetailOrderDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		OrderRecord orderRecord = input.getOrderRecord();
		DetailOrderRecord detailOrderRecord = input.getDetailOrderRecord();

		orderRecord.cancelDetailOrder(detailOrderRecord);

		return Event.builder()
			.orderRecord(orderRecord)
			.build();
	}

}
