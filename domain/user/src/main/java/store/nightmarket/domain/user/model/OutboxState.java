package store.nightmarket.domain.user.model;

import java.util.EnumSet;
import java.util.Set;

public enum OutboxState {

	READY,
	PUBLISHED,
	FAILED;

	private Set<OutboxState> nextStates;

	static {
		READY.nextStates = EnumSet.of(PUBLISHED, FAILED);
		PUBLISHED.nextStates = EnumSet.noneOf(OutboxState.class);
		FAILED.nextStates = EnumSet.of(PUBLISHED);
	}

	public boolean canTransitionTo(OutboxState target) {
		return nextStates.contains(target);
	}

}
