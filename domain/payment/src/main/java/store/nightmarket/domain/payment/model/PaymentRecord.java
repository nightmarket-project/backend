package store.nightmarket.domain.payment.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.model.id.PaymentRecordId;
import store.nightmarket.domain.payment.model.id.UserId;

@Getter
public class PaymentRecord extends BaseModel<PaymentRecordId> {

	private UserId userId;
	private List<DetailPaymentRecord> detailPaymentRecordList;

	private PaymentRecord(
		PaymentRecordId id,
		UserId userId,
		List<DetailPaymentRecord> detailPaymentRecordList
	) {
		super(id);
		this.userId = userId;
		this.detailPaymentRecordList = detailPaymentRecordList;
	}

	private PaymentRecord(
		PaymentRecordId id,
		LocalDateTime createdAt,
		UserId userId,
		List<DetailPaymentRecord> detailPaymentRecordList
	) {
		super(id, createdAt);
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

	public static PaymentRecord newInstanceWithCreatedAt(
		PaymentRecordId id,
		LocalDateTime createdAt,
		UserId userId,
		List<DetailPaymentRecord> detailPaymentRecordList
	) {
		return new PaymentRecord(
			id,
			createdAt,
			userId,
			detailPaymentRecordList
		);
	}

	public PaymentRecordId getPaymentRecordId() {
		return internalId();
	}

	public void requestPayment() throws PaymentException {
		detailPaymentRecordList.forEach(DetailPaymentRecord::submit);
	}

	public void completePayment() {
		detailPaymentRecordList.forEach(DetailPaymentRecord::complete);
	}

	public void rejectPayment() {
		detailPaymentRecordList.forEach(DetailPaymentRecord::reject);
	}

	public void cancelPayment() {
		detailPaymentRecordList.forEach(DetailPaymentRecord::cancel);
	}

	public void cancelDetailPayment(DetailPaymentRecord detailPaymentRecord) {
		detailPaymentRecordList.stream()
			.filter(d -> d.equals(detailPaymentRecord))
			.findFirst()
			.ifPresentOrElse(
				DetailPaymentRecord::cancel,
				() -> {
					throw new PaymentException("Payment not found");
				}
			);
	}

}
