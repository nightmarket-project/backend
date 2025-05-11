package store.nightmarket.domain.delivery.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.Address;
import store.nightmarket.domain.delivery.valueobject.DeliveryRecordId;
import store.nightmarket.domain.delivery.valueobject.DeliveryTrackingRecordId;
import store.nightmarket.domain.delivery.valueobject.Location;
import store.nightmarket.domain.delivery.valueobject.UserId;

public class DeliveryTestUtil {

	public static DeliveryTrackingRecord makeDeliveryTrackingRecord(
		UUID id,
		String location,
		DetailDeliveryState state,
		String content
	) {

		return DeliveryTrackingRecord.newInstance(
			new DeliveryTrackingRecordId(id),
			LocalDateTime.now(),
			new Location(location),
			state,
			content
		);
	}

	public static DeliveryRecord makeDeliveryRecord(List<DeliveryTrackingRecord> list) {
		return DeliveryRecord.newInstance(
			new DeliveryRecordId(UUID.randomUUID()),
			new Address("111111", "하늘로 111-111", "구름아파트 111동 111호"),
			new UserId(UUID.randomUUID()),
			list
		);
	}

	public static AddDeliveryTrackRecordDomainServiceDto.Input makeAddDeliveryTrackInput(
		DeliveryRecord deliveryRecord,
		DeliveryTrackingRecord deliveryTrackingRecord
	) {

		return AddDeliveryTrackRecordDomainServiceDto.Input.builder()
			.deliveryRecord(deliveryRecord)
			.deliveryTrackingRecord(deliveryTrackingRecord)
			.build();
	}

}
