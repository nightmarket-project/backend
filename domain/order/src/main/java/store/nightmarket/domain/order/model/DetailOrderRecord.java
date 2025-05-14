package store.nightmarket.domain.order.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductId;
import store.nightmarket.domain.order.valueobject.Quantity;

@Getter
public class DetailOrderRecord extends BaseModel<DetailOrderRecordId> {

	private ProductId productId;
	private Quantity quantity;
	private DetailOrderState state;

	public DetailOrderRecord(
		DetailOrderRecordId id,
		ProductId productId,
		Quantity quantity,
		DetailOrderState state
	) {

		super(id);
		this.productId = productId;
		this.quantity = quantity;
		this.state = state;
	}

	public static DetailOrderRecord newInstance(
		DetailOrderRecordId id,
		ProductId productId,
		Quantity quantity,
		DetailOrderState state
	) {

		return new DetailOrderRecord(
			id,
			productId,
			quantity,
			state
		);
	}

	public void submit() {
		if (!state.canTransitionTo(DetailOrderState.SUBMITTED)) {
			throw new OrderException("cannot change state to submitted");
		}
		this.state = DetailOrderState.SUBMITTED;
	}

	public void cancel() {
		if (!state.canTransitionTo(DetailOrderState.CANCELED)) {
			throw new OrderException("cannot change state to canceled");
		}
		this.state = DetailOrderState.CANCELED;
	}

	public boolean isSubmitted() {
		return state.equals(DetailOrderState.SUBMITTED);
	}

	public boolean isCanceled() {
		return state.equals(DetailOrderState.CANCELED);
	}

	public boolean isMatched(DetailOrderRecord detailOrderRecord) {
		return getId().equals(detailOrderRecord.getId());
	}

}
