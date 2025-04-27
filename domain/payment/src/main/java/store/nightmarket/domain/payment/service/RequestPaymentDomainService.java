package store.nightmarket.domain.payment.service;

import static store.nightmarket.domain.payment.service.dto.RequestPaymentDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.PaymentRecord;

@Component
public class RequestPaymentDomainService
	implements BaseDomainService<Command, Event> {

	@Override
	public Event execute(Command input) {
		PaymentRecord paymentRecord = input.getPaymentRecord();

		paymentRecord.requestPay();

		return Event.builder()
			.paymentRecord(paymentRecord)
			.build();
	}

}
