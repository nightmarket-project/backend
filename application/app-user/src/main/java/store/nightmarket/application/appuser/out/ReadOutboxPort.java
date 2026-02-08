package store.nightmarket.application.appuser.out;

import java.util.List;
import java.util.Optional;

import store.nightmarket.application.appuser.auth.exception.OutboxException;
import store.nightmarket.domain.user.model.Outbox;

public interface ReadOutboxPort {

	Optional<Outbox> read(Long id);

	default Outbox readOrThrow(Long id) {
		return read(id)
			.orElseThrow(() -> new OutboxException("Not Found Outbox"));
	}

	List<Outbox> readPublishTarget();

}
