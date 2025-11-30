package store.nightmarket.domain.payment.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.model.id.DetailPaymentRecordId;

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

	private DetailPaymentRecord(
		DetailPaymentRecordId id,
		LocalDateTime createdAt,
		Product product,
		DetailPaymentState state
	) {
		super(id, createdAt);
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

	public static DetailPaymentRecord newInstanceWithCreatedAt(
		DetailPaymentRecordId id,
		LocalDateTime createdAt,
		Product product,
		DetailPaymentState state
	) {
		return new DetailPaymentRecord(
			id,
			createdAt,
			product,
			state
		);
	}

	public DetailPaymentRecordId getDetailPaymentRecordId() {
		return internalId();
	}

	public void submit() {
		if (!state.canTransitionTo(DetailPaymentState.SUBMITTED)) {
			throw new PaymentException("cannot be able to change state to submitted");
		}
		this.state = DetailPaymentState.SUBMITTED;
	}

	public void complete() {
		if (!state.canTransitionTo(DetailPaymentState.COMPLETED)) {
			throw new PaymentException("cannot be able to change state to completed");
		}
		this.state = DetailPaymentState.COMPLETED;
	}

	public void reject() {
		if (!state.canTransitionTo(DetailPaymentState.REJECTED)) {
			throw new PaymentException("cannot be able to change state to rejected");
		}
		this.state = DetailPaymentState.REJECTED;
	}

	public void cancel() {
		if (!state.canTransitionTo(DetailPaymentState.CANCELED)) {
			throw new PaymentException("cannot be able to change state to cancelled");
		}
		this.state = DetailPaymentState.CANCELED;
	}

}
