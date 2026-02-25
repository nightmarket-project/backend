package store.nightmarket.application.appoutbox.out;

import java.util.List;
import java.util.Optional;

import store.nightmarket.domain.outbox.exception.OutboxException;
import store.nightmarket.domain.outbox.model.Outbox;

public interface ReadOutboxPort {

	Optional<Outbox> read(Long id);

	default Outbox readOrThrow(Long id) {
		return read(id)
			.orElseThrow(() -> new OutboxException("Not Found Outbox"));
	}

	List<Outbox> readPublishTarget(int limit);

}
