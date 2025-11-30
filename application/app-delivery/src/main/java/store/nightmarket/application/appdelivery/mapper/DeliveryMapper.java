package store.nightmarket.application.appdelivery.mapper;

import java.util.ArrayList;
import java.util.List;

import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.model.id.DeliveryRecordId;
import store.nightmarket.domain.delivery.model.id.UserId;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryRecordEntity;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryTrackingRecordEntity;

public class DeliveryMapper {

	public static DeliveryRecord toDomain(DeliveryRecordEntity entity) {
		List<DeliveryTrackingRecord> trackingRecords = DeliveryTrackingRecordMapper
			.toDomainList(entity.getDeliveryTrackingRecords());

		return DeliveryRecord.newInstanceWithCreatedAt(
			new DeliveryRecordId(entity.getId()),
			entity.getCreatedAt(),
			AddressMapper.toDomain(entity.getAddressEntity()),
			new UserId(entity.getUserId()),
			trackingRecords
		);
	}

	public static DeliveryRecordEntity toEntity(DeliveryRecord domain) {

		DeliveryRecordEntity entity = new DeliveryRecordEntity(
			domain.getDeliveryRecordId().getId(),
			domain.getCreatedAt(),
			AddressMapper.toEntity(domain.getAddress()),
			domain.getUserId().getId(),
			new ArrayList<>()
		);

		List<DeliveryTrackingRecordEntity> entityList = DeliveryTrackingRecordMapper
			.toEntityList(domain.getDeliveryTrackingRecordList(), entity);

		entity.getDeliveryTrackingRecords().addAll(entityList);

		return entity;
	}

}
