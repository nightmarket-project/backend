package store.nightmarket.application.appdelivery.mapper;

import store.nightmarket.common.out.persistence.jpa.entity.delivery.model.DeliveryTrackingRecordEntity;
import store.nightmarket.common.out.persistence.jpa.entity.delivery.valueobject.DetailDeliveryStateEntity;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.DeliveryTrackingRecordId;
import store.nightmarket.domain.delivery.valueobject.Location;

public class DeliveryTrackingRecordMapper {

	public static DeliveryTrackingRecord toDomain(DeliveryTrackingRecordEntity entity) {
		return new DeliveryTrackingRecord(
			new DeliveryTrackingRecordId(entity.getId()),
			entity.getTime(),
			new Location(entity.getLocationEntity().getName()),
			DetailDeliveryState.valueOf(entity.getState().name()),
			entity.getContent()
		);
	}

	public static DeliveryTrackingRecordEntity toEntity(DeliveryTrackingRecord domain) {
		return new DeliveryTrackingRecordEntity(
			domain.getTime(),
			LocationMapper.toEntity(domain.getLocation()),
			DetailDeliveryStateEntity.valueOf(domain.getState().name()),
			domain.getContent()
		);
	}

}