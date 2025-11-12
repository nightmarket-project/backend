package store.nightmarket.application.apppayment.in;

import store.nightmarket.application.apppayment.in.dto.PaymentRequestEvent;

public interface PaymentRequestEventListener {

	public void handleEvent(PaymentRequestEvent event);

}
