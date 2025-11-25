package store.nightmarket.application.apporder.out;

import store.nightmarket.application.apporder.out.dto.PaymentRequestEvent;

public interface PaymentRequestEventPublisher {

	void publishEvent(PaymentRequestEvent event);

}