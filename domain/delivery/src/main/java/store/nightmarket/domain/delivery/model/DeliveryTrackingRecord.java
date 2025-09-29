package store.nightmarket.domain.delivery.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.DeliveryTrackingRecordId;
import store.nightmarket.domain.delivery.valueobject.Location;

@Getter
public class DeliveryTrackingRecord extends BaseModel<DeliveryTrackingRecordId> {

	private LocalDateTime time;
	private Location location;
	private DetailDeliveryState state;
	private String content;

	private DeliveryTrackingRecord(
		DeliveryTrackingRecordId id,
		LocalDateTime time,
		Location location,
		DetailDeliveryState state,
		String content
	) {
		super(id);
		this.time = time;
		this.location = location;
		this.state = state;
		this.content = content;
	}

	private DeliveryTrackingRecord(
		DeliveryTrackingRecordId id,
		LocalDateTime createdAt,
		LocalDateTime time,
		Location location,
		DetailDeliveryState state,
		String content
	) {
		super(id, createdAt);
		this.time = time;
		this.location = location;
		this.state = state;
		this.content = content;
	}

	public static DeliveryTrackingRecord newInstance(
		DeliveryTrackingRecordId id,
		LocalDateTime time,
		Location location,
		DetailDeliveryState state,
		String content
	) {
		return new DeliveryTrackingRecord(
			id,
			time,
			location,
			state,
			content
		);
	}

	public static DeliveryTrackingRecord newInstanceWithCreatedAt(
		DeliveryTrackingRecordId id,
		LocalDateTime createdAt,
		LocalDateTime time,
		Location location,
		DetailDeliveryState state,
		String content
	) {
		return new DeliveryTrackingRecord(
			id,
			createdAt,
			time,
			location,
			state,
			content
		);
	}

	public DeliveryTrackingRecordId getDeliveryTrackingRecordId() {
		return internalId();
	}

}
