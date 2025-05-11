package store.nightmarket.domain.order.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.UserId;

@Getter
public class OrderRecord extends BaseModel<OrderRecordId> {

	private List<DetailOrderRecord> detailOrderRecordList;
	private Address address;
	private LocalDate orderDate;
	private UserId userId;

	public OrderRecord(
		OrderRecordId id,
		List<DetailOrderRecord> detailOrderRecordList,
		Address address,
		LocalDate orderDate,
		UserId userId
	) {

		super(id);
		this.detailOrderRecordList = detailOrderRecordList;
		this.address = address;
		this.orderDate = orderDate;
		this.userId = userId;
	}

	public static OrderRecord newInstance(
		OrderRecordId id,
		List<DetailOrderRecord> detailOrderRecordList,
		Address address,
		LocalDate orderDate,
		UserId userId
	) {

		return new OrderRecord(
			id,
			detailOrderRecordList,
			address,
			orderDate,
			userId
		);
	}

	public void requestOrder() {
		detailOrderRecordList.forEach(DetailOrderRecord::submit);
	}

	public void cancelAllOrder() {
		detailOrderRecordList.forEach(DetailOrderRecord::cancel);
	}

	public void cancelDetailOrder(DetailOrderRecord detailOrderRecord) {
		DetailOrderRecord detail = detailOrderRecordList.stream()
			.filter(d -> d.isMatched(detailOrderRecord))
			.findFirst()
			.orElseThrow(() -> new OrderException("Detail order not found"));

		detail.cancel();
	}

}
