package store.nightmarket.domain.payment.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.valueobject.DetailPaymentRecordId;

@Getter
public class DetailPaymentRecord extends BaseModel<DetailPaymentRecordId> {

    private Product product;
    private DetailPaymentState state;

    private DetailPaymentRecord(
            DetailPaymentRecordId id,
            Product product,
            DetailPaymentState state
    ) {
        super(id);
        this.product = product;
        this.state = state;
    }

    public static DetailPaymentRecord newInstance(
            DetailPaymentRecordId id,
            Product product,
            DetailPaymentState state
    ) {
        return new DetailPaymentRecord(
                id,
                product,
                state
        );
    }

    public void submitDetailPayment() {
        if (!state.canTransitionTo(DetailPaymentState.SUBMITTED)) {
            throw new PaymentException("cannot be able to change state to submitted");
        }
        this.state = DetailPaymentState.SUBMITTED;
    }

}
