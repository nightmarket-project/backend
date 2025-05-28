package store.nightmarket.application.apporder.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.UserId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public class OrderRecordMapper {

	public static OrderRecord toDomain(OrderRecordEntity entity) {
		List<DetailOrderRecord> detailOrderRecordList = entity.getDetailOrderRecordList()
			.stream()
			.map(DetailOrderRecordMapper::toDomain)
			.collect(Collectors.toList());

		return OrderRecord.newInstance(
			new OrderRecordId(entity.getId()),
			AddressMapper.toDomain(entity.getAddressEntity()),
			entity.getOrderDate(),
			new UserId(entity.getUserId()),
			detailOrderRecordList
		);
	}

	public static OrderRecordEntity toEntity(OrderRecord domain) {
		List<DetailOrderRecordEntity> detailOrderRecordEntities = domain.getDetailOrderRecordList()
			.stream()
			.map(DetailOrderRecordMapper::toEntity)
			.collect(Collectors.toList());

		return new OrderRecordEntity(
			AddressMapper.toEntity(domain.getAddress()),
			domain.getOrderDate(),
			domain.getUserId().getId(),
			detailOrderRecordEntities
		);
	}

}
