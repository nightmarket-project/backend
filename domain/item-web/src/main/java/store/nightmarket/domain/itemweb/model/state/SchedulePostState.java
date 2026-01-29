package store.nightmarket.domain.itemweb.model.state;

import java.util.EnumSet;
import java.util.Set;

public enum SchedulePostState {

	READY,
	DONE,
	CANCELED;

	private Set<SchedulePostState> nextStates;

	static {
		READY.nextStates = EnumSet.of(DONE, CANCELED);
		DONE.nextStates = EnumSet.noneOf(SchedulePostState.class);
		CANCELED.nextStates = EnumSet.noneOf(SchedulePostState.class);

	}

	public boolean canTransitionTo(SchedulePostState target) {
		return nextStates.contains(target);
	}

}
