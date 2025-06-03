package store.nightmarket.application.apporder.out;

import java.util.Optional;
import java.util.UUID;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.OrderRecord;

public interface ReadOrderPort {

	Optional<OrderRecord> read(UUID id);

	default OrderRecord readOrThrow(UUID id) {
		return read(id)
			.orElseThrow(() -> new OrderException("Not Found OrderRecord"));
	}
	
}
