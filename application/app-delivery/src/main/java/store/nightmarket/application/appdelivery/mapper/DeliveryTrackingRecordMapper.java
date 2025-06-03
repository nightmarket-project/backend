package store.nightmarket.application.appdelivery.mapper;

import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.DeliveryTrackingRecordId;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryTrackingRecordEntity;

public class DeliveryTrackingRecordMapper {

	public static DeliveryTrackingRecord toDomain(DeliveryTrackingRecordEntity entity) {
		return new DeliveryTrackingRecord(
			new DeliveryTrackingRecordId(entity.getId()),
			entity.getTime(),
			LocationMapper.toDomain(entity.getLocationEntity()),
			DetailDeliveryState.valueOf(entity.getState().name()),
			entity.getContent()
		);
	}

	public static DeliveryTrackingRecordEntity toEntity(DeliveryTrackingRecord domain) {
		return new DeliveryTrackingRecordEntity(
			domain.getTime(),
			LocationMapper.toEntity(domain.getLocation()),
			domain.getState(),
			domain.getContent()
		);
	}

}