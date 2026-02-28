package store.nightmarket.domain.outbox.model;

import java.util.EnumSet;
import java.util.Set;

public enum OutboxState {

	READY,
	PUBLISHED,
	FAILED,
	DEAD;

	private Set<OutboxState> nextStates;

	static {
		READY.nextStates = EnumSet.of(PUBLISHED, FAILED);
		PUBLISHED.nextStates = EnumSet.noneOf(OutboxState.class);
		FAILED.nextStates = EnumSet.of(PUBLISHED, DEAD);
		DEAD.nextStates = EnumSet.noneOf(OutboxState.class);
	}

	public boolean canTransitionTo(OutboxState target) {
		return nextStates.contains(target);
	}

}
