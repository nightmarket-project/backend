package store.nightmarket.domain.payment.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.service.dto.CancelDetailPaymentDomainServiceDto.Event;
import store.nightmarket.domain.payment.service.dto.CancelDetailPaymentDomainServiceDto.Input;

public class CancelDetailPaymentDomainService
        implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        PaymentRecord paymentRecord = input.getPaymentRecord();
        DetailPaymentRecord detailPaymentRecord = input.getDetailPaymentRecord();

        paymentRecord.cancelDetailPayment(detailPaymentRecord);

        return Event.builder()
                .paymentRecord(paymentRecord)
                .build();
    }

}
