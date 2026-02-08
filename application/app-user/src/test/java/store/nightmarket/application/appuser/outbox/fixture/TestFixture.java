package store.nightmarket.application.appuser.outbox.fixture;

import java.util.UUID;

import store.nightmarket.domain.user.model.Outbox;
import store.nightmarket.domain.user.model.OutboxState;
import store.nightmarket.domain.user.model.id.OutboxId;

public class TestFixture {

	public static Outbox createOutbox(OutboxId id) {
		return Outbox.newInstanceWithId(
			id,
			"User",
			UUID.randomUUID().toString(),
			"UserCreatedEvent",
			"payload",
			OutboxState.READY
		);
	}

}
