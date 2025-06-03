package store.nightmarket.application.appdelivery.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.valueobject.DeliveryRecordId;
import store.nightmarket.domain.delivery.valueobject.UserId;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryRecordEntity;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryTrackingRecordEntity;

public class DeliveryMapper {

	public static DeliveryRecord toDomain(DeliveryRecordEntity entity) {
		List<DeliveryTrackingRecord> trackingRecords = entity.getDeliveryTrackingRecords()
			.stream()
			.map(DeliveryTrackingRecordMapper::toDomain)
			.collect(Collectors.toList());

		return DeliveryRecord.newInstance(
			new DeliveryRecordId(entity.getId()),
			AddressMapper.toDomain(entity.getAddressEntity()),
			new UserId(entity.getUserId()),
			trackingRecords
		);
	}

	public static DeliveryRecordEntity toEntity(DeliveryRecord domain) {
		List<DeliveryTrackingRecordEntity> trackingEntities = domain.getDeliveryTrackingRecordList()
			.stream()
			.map(DeliveryTrackingRecordMapper::toEntity)
			.collect(Collectors.toList());

		return new DeliveryRecordEntity(
			AddressMapper.toEntity(domain.getAddress()),
			domain.getUserId().getId(),
			trackingEntities
		);
	}

}
