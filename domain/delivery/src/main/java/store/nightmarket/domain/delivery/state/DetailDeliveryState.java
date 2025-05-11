package store.nightmarket.domain.delivery.state;

import java.util.EnumSet;
import java.util.Set;

public enum DetailDeliveryState {

	NONE,
	PREPARING,
	SHIPPED,
	IN_DELIVERY,
	DELIVERED,
	CANCELLED,
	RETURNED;

	private Set<DetailDeliveryState> nextStates;

	static {
		NONE.nextStates = EnumSet.of(PREPARING);
		PREPARING.nextStates = EnumSet.of(SHIPPED);
		SHIPPED.nextStates = EnumSet.of(IN_DELIVERY);
		IN_DELIVERY.nextStates = EnumSet.of(IN_DELIVERY, DELIVERED);
		DELIVERED.nextStates = EnumSet.of(RETURNED);
		CANCELLED.nextStates = EnumSet.noneOf(DetailDeliveryState.class);
		RETURNED.nextStates = EnumSet.noneOf(DetailDeliveryState.class);
	}

	public boolean canTransitionTo(DetailDeliveryState target) {
		return nextStates.contains(target);
	}

}


