package store.nightmarket.domain.outbox.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.outbox.exception.OutboxException;
import store.nightmarket.domain.outbox.model.id.OutboxId;

@Getter
public class Outbox extends BaseModel<OutboxId> {

	private static final int MAX_RETRY_CNT = 10;
	private String eventType;
	private String payload;
	private OutboxState state;
	private int retryCnt;

	private Outbox(
		String eventType,
		String payload,
		OutboxState state,
		int retryCnt
	) {
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
		this.retryCnt = retryCnt;
	}

	private Outbox(
		OutboxId id,
		String eventType,
		String payload,
		OutboxState state,
		int retryCnt
	) {
		super(id);
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
		this.retryCnt = retryCnt;
	}

	public static Outbox newInstance(
		String eventType,
		String payload,
		OutboxState state
	) {
		return new Outbox(
			eventType,
			payload,
			state,
			0
		);
	}

	public static Outbox newInstanceWithId(
		OutboxId id,
		String eventType,
		String payload,
		OutboxState state
	) {
		return new Outbox(
			id,
			eventType,
			payload,
			state,
			0
		);
	}

	public OutboxId getOutboxId() {
		return internalId();
	}

	public void ready() {
		if (!state.canTransitionTo(OutboxState.READY)) {
			throw new OutboxException("cannot change state to ready");
		}
		this.state = OutboxState.READY;
	}

	public void publish() {
		if (!state.canTransitionTo(OutboxState.PUBLISHED)) {
			throw new OutboxException("cannot change state to published");
		}
		this.state = OutboxState.PUBLISHED;
	}

	public void fail() {
		if (this.state.equals(OutboxState.FAILED)) {
			return;
		}

		if (!state.canTransitionTo(OutboxState.FAILED)) {
			throw new OutboxException("cannot change state to failed");
		}
		this.state = OutboxState.FAILED;
	}

	public void dead() {
		if (!state.canTransitionTo(OutboxState.DEAD)) {
			throw new OutboxException("cannot change state to dead");
		}
		this.state = OutboxState.DEAD;
	}

	public void failOrDead() {
		increaseRetryCnt();

		if (this.retryCnt >= MAX_RETRY_CNT) {
			dead();
		} else {
			fail();
		}
	}

	private void increaseRetryCnt() {
		this.retryCnt++;
	}

}
