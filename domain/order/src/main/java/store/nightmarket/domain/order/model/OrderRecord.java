package store.nightmarket.domain.order.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.model.id.OrderRecordId;
import store.nightmarket.domain.order.model.id.UserId;

@Getter
public class OrderRecord extends BaseModel<OrderRecordId> {

	private Address address;
	private LocalDate orderDate;
	private UserId userId;
	private List<DetailOrderRecord> detailOrderRecordList;

	private OrderRecord(
		OrderRecordId id,
		Address address,
		LocalDate orderDate,
		UserId userId,
		List<DetailOrderRecord> detailOrderRecordList
	) {
		super(id);
		this.address = address;
		this.orderDate = orderDate;
		this.userId = userId;
		this.detailOrderRecordList =
			detailOrderRecordList != null ? new ArrayList<>(detailOrderRecordList) : new ArrayList<>();
	}

	private OrderRecord(
		OrderRecordId id,
		LocalDateTime createdAt,
		Address address,
		LocalDate orderDate,
		UserId userId,
		List<DetailOrderRecord> detailOrderRecordList
	) {
		super(id, createdAt);
		this.address = address;
		this.orderDate = orderDate;
		this.userId = userId;
		this.detailOrderRecordList = detailOrderRecordList;
	}

	public static OrderRecord newInstance(
		OrderRecordId id,
		Address address,
		LocalDate orderDate,
		UserId userId,
		List<DetailOrderRecord> detailOrderRecordList
	) {
		return new OrderRecord(
			id,
			address,
			orderDate,
			userId,
			detailOrderRecordList
		);
	}

	public static OrderRecord newInstanceWithCreatedAt(
		OrderRecordId id,
		LocalDateTime createdAt,
		Address address,
		LocalDate orderDate,
		UserId userId,
		List<DetailOrderRecord> detailOrderRecordList
	) {
		return new OrderRecord(
			id,
			createdAt,
			address,
			orderDate,
			userId,
			detailOrderRecordList
		);
	}

	public OrderRecordId getOrderRecordId() {
		return internalId();
	}

	public void requestOrder() {
		detailOrderRecordList.forEach(DetailOrderRecord::submit);
	}

	public void cancelAllOrder() {
		detailOrderRecordList.forEach(DetailOrderRecord::cancel);
	}

	public void cancelDetailOrder(DetailOrderRecord detailOrderRecord) {
		detailOrderRecordList.stream()
			.filter(d -> d.equals(detailOrderRecord))
			.findFirst()
			.ifPresentOrElse(
				DetailOrderRecord::cancel,
				() -> {
					throw new OrderException("Detail order not found");
				}
			);
	}

}
