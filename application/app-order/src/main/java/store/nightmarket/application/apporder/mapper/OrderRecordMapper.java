package store.nightmarket.application.apporder.mapper;

import java.util.ArrayList;
import java.util.List;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.UserId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public class OrderRecordMapper {

	public static OrderRecord toDomain(OrderRecordEntity entity) {
		List<DetailOrderRecord> domainDetails = DetailOrderRecordMapper
			.toDomainList(entity.getDetailOrderRecordList());

		return OrderRecord.newInstance(
			new OrderRecordId(entity.getId()),
			AddressMapper.toDomain(entity.getAddressEntity()),
			entity.getOrderDate(),
			new UserId(entity.getUserId()),
			domainDetails
		);
	}

	public static OrderRecordEntity toEntity(OrderRecord domain) {
		OrderRecordEntity entity = new OrderRecordEntity(
			domain.getOrderRecordId().getId(),
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
