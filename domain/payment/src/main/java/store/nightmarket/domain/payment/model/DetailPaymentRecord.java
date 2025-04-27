package store.nightmarket.domain.payment.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.valueobject.DetailPaymentRecordId;
import store.nightmarket.domain.payment.valueobject.Price;

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

}
