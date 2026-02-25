package store.nightmarket.application.appoutbox.out;

import store.nightmarket.domain.outbox.model.Outbox;

public interface SaveOutboxPort {

	void save(Outbox outbox);

	void saveWithId(Outbox outbox);

}
