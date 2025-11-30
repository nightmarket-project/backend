package store.nightmarket.application.appdelivery.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.model.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.model.id.DeliveryTrackingRecordId;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryRecordEntity;
import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryTrackingRecordEntity;

public class DeliveryTrackingRecordMapper {

	public static DeliveryTrackingRecord toDomain(DeliveryTrackingRecordEntity entity) {
		return DeliveryTrackingRecord.newInstanceWithCreatedAt(
			new DeliveryTrackingRecordId(entity.getId()),
			entity.getCreatedAt(),
			entity.getTime(),
			LocationMapper.toDomain(entity.getLocationEntity()),
			DetailDeliveryState.valueOf(entity.getState().name()),
			entity.getContent()
		);
	}

	public static DeliveryTrackingRecordEntity toEntity(
		DeliveryTrackingRecord domain,
		DeliveryRecordEntity deliveryRecordEntity
	) {
		return new DeliveryTrackingRecordEntity(
			domain.getDeliveryTrackingRecordId().getId(),
			domain.getCreatedAt(),
			domain.getTime(),
			LocationMapper.toEntity(domain.getLocation()),
			domain.getState(),
			domain.getContent(),
			deliveryRecordEntity
		);
	}

	public static List<DeliveryTrackingRecord> toDomainList(List<DeliveryTrackingRecordEntity> entityList) {
		return entityList.stream()
			.map(DeliveryTrackingRecordMapper::toDomain)
			.collect(Collectors.toList());
	}

	public static List<DeliveryTrackingRecordEntity> toEntityList(
		List<DeliveryTrackingRecord> domainList,
		DeliveryRecordEntity deliveryRecordEntity
	) {
		return domainList.stream()
			.map(domain -> toEntity(domain, deliveryRecordEntity))
			.collect(Collectors.toList());
	}

}
