package store.nightmarket.application.apporder.out.mapper;

import java.util.ArrayList;
import java.util.List;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.id.OrderRecordId;
import store.nightmarket.domain.order.model.id.UserId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public class OrderRecordMapper {

	public static OrderRecord toDomain(OrderRecordEntity entity) {
		List<DetailOrderRecord> domainDetails = DetailOrderRecordMapper
			.toDomainList(entity.getDetailOrderRecordList());

		return OrderRecord.newInstanceWithCreatedAt(
			new OrderRecordId(entity.getId()),
			entity.getCreatedAt(),
			AddressMapper.toDomain(entity.getAddressEntity()),
			entity.getOrderDate(),
			new UserId(entity.getUserId()),
			domainDetails
		);
	}

	public static OrderRecordEntity toEntity(OrderRecord domain) {
		OrderRecordEntity entity = new OrderRecordEntity(
			domain.getOrderRecordId().getId(),
			domain.getCreatedAt(),
			AddressMapper.toEntity(domain.getAddress()),
			domain.getOrderDate(),
			domain.getUserId().getId(),
			new ArrayList<>()
		);

		List<DetailOrderRecordEntity> entityList = DetailOrderRecordMapper
			.toEntityList(domain.getDetailOrderRecordList(), entity);

		entity.getDetailOrderRecordList().addAll(entityList);

		return entity;
	}

}
