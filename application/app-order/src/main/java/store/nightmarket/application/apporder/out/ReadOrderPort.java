package store.nightmarket.application.apporder.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.id.OrderRecordId;
import store.nightmarket.domain.order.model.id.UserId;

public interface ReadOrderPort {

	Optional<OrderRecord> read(OrderRecordId id);

	default OrderRecord readOrThrow(OrderRecordId id) {
		return read(id)
			.orElseThrow(() -> new OrderException("Not Found OrderRecord"));
	}

	Page<OrderRecord> readAllByUserId(UserId id, Pageable pageable);

}
