package store.nightmarket.domain.payment.service;

import org.springframework.stereotype.Component;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.service.dto.RequestPaymentDomainServiceDto.*;

@Component
public class RequestPaymentDomainService
        implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        PaymentRecord paymentRecord = input.getPaymentRecord();

        paymentRecord.requestPayment();

        return Event.builder()
                .paymentRecord(paymentRecord)
                .build();
    }

}
