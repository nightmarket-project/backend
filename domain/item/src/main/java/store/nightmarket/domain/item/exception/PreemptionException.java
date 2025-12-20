package store.nightmarket.domain.item.exception;

import store.nightmarket.common.exception.DomainException;

public class PreemptionException extends DomainException {

	public PreemptionException() {
	}

	public PreemptionException(String message) {
		super(message);
	}

}
