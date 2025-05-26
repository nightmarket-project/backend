package store.nightmarket.domain.payment.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;

public class CancelDetailPaymentDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final PaymentRecord paymentRecord;
        private final DetailPaymentRecord detailPaymentRecord;

    }

    @Builder
    @Getter
    public static class Event {

        private final PaymentRecord paymentRecord;

    }

}
