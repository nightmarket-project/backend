package store.nightmarket.application.apporder.out;

import store.nightmarket.domain.order.model.OrderRecord;

public interface SaveOrderPort {

	void save(OrderRecord orderRecord);

}
