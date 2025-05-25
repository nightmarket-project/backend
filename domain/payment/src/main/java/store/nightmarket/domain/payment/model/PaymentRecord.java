package store.nightmarket.domain.payment.model;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.valueobject.PaymentRecordId;
import store.nightmarket.domain.payment.valueobject.UserId;

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

}
