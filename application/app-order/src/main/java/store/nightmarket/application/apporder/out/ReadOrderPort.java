package store.nightmarket.application.apporder.out;

import java.util.Optional;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.OrderRecordId;

public interface ReadOrderPort {

	Optional<OrderRecord> read(OrderRecordId id);

	default OrderRecord readOrThrow(OrderRecordId id) {
		return read(id)
			.orElseThrow(() -> new OrderException("Not Found OrderRecord"));
	}

}
