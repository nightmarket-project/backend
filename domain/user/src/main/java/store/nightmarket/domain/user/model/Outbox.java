package store.nightmarket.domain.user.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.user.exception.OutboxException;
import store.nightmarket.domain.user.model.id.OutboxId;

@Getter
public class Outbox extends BaseModel<OutboxId> {

	private String aggregateType;
	private String aggregateId;
	private String eventType;
	private String payload;
	private OutboxState state;

	private Outbox(
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
	}

	private Outbox(
		OutboxId id,
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		super(id);
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
	}

	public static Outbox newInstance(
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		return new Outbox(
			aggregateType,
			aggregateId,
			eventType,
			payload,
			state
		);
	}

	public static Outbox newInstanceWithId(
		OutboxId id,
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		return new Outbox(
			id,
			aggregateType,
			aggregateId,
			eventType,
			payload,
			state
		);
	}

	public OutboxId getOutboxId() {
		return internalId();
	}

	public void publish() {
		if (!state.canTransitionTo(OutboxState.PUBLISHED)) {
			throw new OutboxException("cannot change state to published");
		}
		this.state = OutboxState.PUBLISHED;
	}

	public void fail() {
		if (!state.canTransitionTo(OutboxState.FAILED)) {
			throw new OutboxException("cannot change state to failed");
		}
		this.state = OutboxState.FAILED;
	}

	public boolean isDispatchTarget() {
		return state.equals(OutboxState.READY) || state.equals(OutboxState.FAILED);
	}

}
