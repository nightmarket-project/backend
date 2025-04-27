package store.nightmarket.domain.payment.state;

public enum DetailPaymentState {

	NONE,
	SUBMITTED,
	COMPLETED,
	REJECTED,
	CANCELED;

	public boolean isAbleChangeToSubmitted() {
		return DetailPaymentState.NONE.equals(this);
	}

}
