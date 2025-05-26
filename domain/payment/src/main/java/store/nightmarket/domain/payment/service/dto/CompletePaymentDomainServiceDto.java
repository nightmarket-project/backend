package store.nightmarket.domain.payment.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.payment.model.PaymentRecord;

public class CompletePaymentDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final PaymentRecord paymentRecord;

    }

    @Builder
    @Getter
    public static class Event {

        private final PaymentRecord paymentRecord;

    }

}
