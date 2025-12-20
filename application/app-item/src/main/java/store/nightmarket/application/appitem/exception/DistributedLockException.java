package store.nightmarket.application.appitem.exception;

public class DistributedLockException extends RuntimeException {

	public DistributedLockException() {
	}

	public DistributedLockException(String message) {
		super(message);
	}

}
