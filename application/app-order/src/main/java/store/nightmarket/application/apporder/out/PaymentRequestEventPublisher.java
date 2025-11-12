package store.nightmarket.application.apporder.out;

import store.nightmarket.application.apporder.in.dto.PaymentRequestEvent;

public interface PaymentRequestEventPublisher {

	void publishEvent(PaymentRequestEvent event);

}