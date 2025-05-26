package store.nightmarket.domain.payment.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.service.dto.CancelPaymentDomainServiceDto.*;

public class CancelPaymentDomainService
        implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        PaymentRecord paymentRecord = input.getPaymentRecord();

        paymentRecord.cancelPayment();

        return Event.builder()
                .paymentRecord(paymentRecord)
                .build();
    }

}
