package store.nightmarket.domain.payment.exception;

public class PaymentException extends RuntimeException {

	public PaymentException() {
	}

	public PaymentException(String message) {
		super(message);
	}

}
