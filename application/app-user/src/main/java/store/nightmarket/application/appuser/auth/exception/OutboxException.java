package store.nightmarket.application.appuser.auth.exception;

public class OutboxException extends RuntimeException {

	public OutboxException() {
	}

	public OutboxException(String message) {
		super(message);
	}

}
