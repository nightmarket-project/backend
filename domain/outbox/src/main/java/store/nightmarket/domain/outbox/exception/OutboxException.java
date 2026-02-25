package store.nightmarket.domain.outbox.exception;

public class OutboxException extends RuntimeException {

	public OutboxException() {
	}

	public OutboxException(String message) {
		super(message);
	}

}
