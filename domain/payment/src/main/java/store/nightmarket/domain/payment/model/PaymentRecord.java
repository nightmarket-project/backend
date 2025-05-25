package store.nightmarket.domain.payment.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.valueobject.PaymentRecordId;
import store.nightmarket.domain.payment.valueobject.UserId;

import java.util.List;

@Getter
public class PaymentRecord extends BaseModel<PaymentRecordId> {

    private UserId userId;
    private List<DetailPaymentRecord> detailPaymentRecordList;

    private PaymentRecord(
            PaymentRecordId id,
            UserId userId,
            List<DetailPaymentRecord> detailPaymentRecordList) {

        super(id);
        this.userId = userId;
        this.detailPaymentRecordList = detailPaymentRecordList;
    }

    public static PaymentRecord newInstance(
            PaymentRecordId id,
            UserId userId,
            List<DetailPaymentRecord> detailPaymentRecordList
    ) {
        return new PaymentRecord(
                id,
                userId,
                detailPaymentRecordList
        );
    }

    public void requestPay() {
        detailPaymentRecordList.forEach(DetailPaymentRecord::submitDetailPayment);
    }

    public void completePay() {
        detailPaymentRecordList.forEach(DetailPaymentRecord::completeDetailPayment);
    }

    public void rejectPay() {
        detailPaymentRecordList.forEach(DetailPaymentRecord::rejectDetailPayment);
    }

    public void cancelAllPayment() {
        detailPaymentRecordList.forEach(DetailPaymentRecord::cancelDetailPayment);
    }

    public void cancelDetailPayment(DetailPaymentRecord detailPaymentRecord) {
        detailPaymentRecordList.stream()
                .filter(d -> d.equals(detailPaymentRecord))
                .findFirst()
                .ifPresentOrElse(
                        DetailPaymentRecord::cancelDetailPayment,
                        () -> {
                            throw new PaymentException("Payment not found");
                        }
                );
    }

}
