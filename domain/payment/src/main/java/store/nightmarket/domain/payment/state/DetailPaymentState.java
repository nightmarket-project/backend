package store.nightmarket.domain.payment.state;

import java.util.EnumSet;
import java.util.Set;

public enum DetailPaymentState {

	NONE,
	SUBMITTED,
	COMPLETED,
	REJECTED,
	CANCELED;

	private Set<DetailPaymentState> nextStates;

	static {
		NONE.nextStates = EnumSet.of(SUBMITTED);
		SUBMITTED.nextStates = EnumSet.of(COMPLETED, REJECTED, CANCELED);
	}

	public boolean canTransitionTo(DetailPaymentState target) {
		return nextStates.contains(target);
	}

}
