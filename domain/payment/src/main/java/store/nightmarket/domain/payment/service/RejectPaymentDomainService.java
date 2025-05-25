package store.nightmarket.domain.payment.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.service.dto.RejectPaymentDomainServiceDto.*;

public class RejectPaymentDomainService
        implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        PaymentRecord paymentRecord = input.getPaymentRecord();

        paymentRecord.rejectPay();

        return Event.builder()
                .paymentRecord(paymentRecord)
                .build();
    }

}
