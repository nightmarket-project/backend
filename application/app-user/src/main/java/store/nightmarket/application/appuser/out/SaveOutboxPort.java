package store.nightmarket.application.appuser.out;

import store.nightmarket.domain.user.model.Outbox;

public interface SaveOutboxPort {

	void save(Outbox outbox);

}
