package store.nightmarket.application.apppayment.out;

import store.nightmarket.application.apppayment.out.dto.PaymentSuccessEventDto;

public interface PaymentSuccessEventPublisher {

	void publishEvent(PaymentSuccessEventDto eventDto);

}
