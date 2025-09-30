package store.nightmarket.domain.order.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.domain.order.valueobject.Quantity;

@Getter
public class DetailOrderRecord extends BaseModel<DetailOrderRecordId> {

	private ProductVariantId productVariantId;
	private Quantity quantity;
	private DetailOrderState state;

	private DetailOrderRecord(
		DetailOrderRecordId id,
		ProductVariantId productVariantId,
		Quantity quantity,
		DetailOrderState state
	) {
		super(id);
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.state = state;
	}

	private DetailOrderRecord(
		DetailOrderRecordId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		Quantity quantity,
		DetailOrderState state
	) {
		super(id, createdAt);
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.state = state;
	}

	public static DetailOrderRecord newInstance(
		DetailOrderRecordId id,
		ProductVariantId productVariantId,
		Quantity quantity,
		DetailOrderState state
	) {
		return new DetailOrderRecord(
			id,
			productVariantId,
			quantity,
			state
		);
	}

	public static DetailOrderRecord newInstanceWithCreatedAt(
		DetailOrderRecordId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		Quantity quantity,
		DetailOrderState state
	) {
		return new DetailOrderRecord(
			id,
			createdAt,
			productVariantId,
			quantity,
			state
		);
	}

	public DetailOrderRecordId getDetailOrderRecordId() {
		return internalId();
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		DetailOrderRecord other = (DetailOrderRecord)obj;
		return Objects.equals(productVariantId, other.productVariantId) &&
			Objects.equals(quantity, other.quantity) &&
			state == other.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productVariantId, quantity, state);
	}

}
