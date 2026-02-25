package store.nightmarket.application.appuser.outbox;

import store.nightmarket.domain.outbox.model.OutboxEventType;

public enum UserOutboxEventType implements OutboxEventType {

	USER_CREATED_EVENT;

	@Override
	public String getType() {
		return name();
	}

}