package store.nightmarket.domain.user.exception;

public class OutboxException extends RuntimeException {

	public OutboxException() {
	}

	public OutboxException(String message) {
		super(message);
	}

}
