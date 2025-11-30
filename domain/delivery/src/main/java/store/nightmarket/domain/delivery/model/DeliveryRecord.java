package store.nightmarket.domain.delivery.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.delivery.exception.DeliveryException;
import store.nightmarket.domain.delivery.model.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.Address;
import store.nightmarket.domain.delivery.model.id.DeliveryRecordId;
import store.nightmarket.domain.delivery.model.id.UserId;

@Getter
public class DeliveryRecord extends BaseModel<DeliveryRecordId> {

	private Address address;
	private UserId userId;
	private List<DeliveryTrackingRecord> deliveryTrackingRecordList;

	private DeliveryRecord(
		DeliveryRecordId id,
		Address address,
		UserId userId,
		List<DeliveryTrackingRecord> deliveryTrackingRecordList
	) {
		super(id);
		this.address = address;
		this.userId = userId;
		this.deliveryTrackingRecordList =
			deliveryTrackingRecordList != null ? new ArrayList<>(deliveryTrackingRecordList) : new ArrayList<>();
	}

	private DeliveryRecord(
		DeliveryRecordId id,
		LocalDateTime createdAt,
		Address address,
		UserId userId,
		List<DeliveryTrackingRecord> deliveryTrackingRecordList
	) {
		super(id, createdAt);
		this.address = address;
		this.userId = userId;
		this.deliveryTrackingRecordList =
			deliveryTrackingRecordList != null ? new ArrayList<>(deliveryTrackingRecordList) : new ArrayList<>();
	}

	public static DeliveryRecord newInstance(
		DeliveryRecordId id,
		Address address,
		UserId userId,
		List<DeliveryTrackingRecord> deliveryTrackingRecordList
	) {
		return new DeliveryRecord(
			id,
			address,
			userId,
			deliveryTrackingRecordList
		);
	}

	public static DeliveryRecord newInstanceWithCreatedAt(
		DeliveryRecordId id,
		LocalDateTime createdAt,
		Address address,
		UserId userId,
		List<DeliveryTrackingRecord> deliveryTrackingRecordList
	) {
		return new DeliveryRecord(
			id,
			createdAt,
			address,
			userId,
			deliveryTrackingRecordList
		);
	}

	public DeliveryRecordId getDeliveryRecordId() {
		return internalId();
	}

	public DeliveryTrackingRecord getLatestRecord() {
		return deliveryTrackingRecordList.isEmpty() ? null : deliveryTrackingRecordList.getLast();
	}

	public void addDeliveryTrackingRecord(DeliveryTrackingRecord record) {
		DetailDeliveryState curState = getLatestRecord().getState();
		DetailDeliveryState nextState = record.getState();
		if (!curState.canTransitionTo(nextState)) {
			throw new DeliveryException("Cannot change to " + nextState.toString() + " state");
		}
		this.deliveryTrackingRecordList.add(record);
	}

}
