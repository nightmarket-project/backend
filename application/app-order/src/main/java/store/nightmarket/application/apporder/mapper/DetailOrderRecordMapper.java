package store.nightmarket.application.apporder.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public class DetailOrderRecordMapper {

	public static DetailOrderRecord toDomain(DetailOrderRecordEntity entity) {
		return DetailOrderRecord.newInstance(
			new DetailOrderRecordId(entity.getId()),
			new ProductId(entity.getProductId()),
			QuantityMapper.toDomain(entity.getQuantity()),
			entity.getState()
		);
	}

	public static DetailOrderRecordEntity toEntity(
		DetailOrderRecord domain,
		OrderRecordEntity orderRecordEntity
	) {
		return new DetailOrderRecordEntity(
			domain.getDetailOrderRecordId().getId(),
			domain.getProductId().getId(),
			QuantityMapper.toEntity(domain.getQuantity()),
			domain.getState(),
			orderRecordEntity
		);
	}

	public static List<DetailOrderRecord> toDomainList(List<DetailOrderRecordEntity> entityList) {
		return entityList.stream()
			.map(DetailOrderRecordMapper::toDomain)
			.collect(Collectors.toList());
	}

	public static List<DetailOrderRecordEntity> toEntityList(
		List<DetailOrderRecord> domainList,
		OrderRecordEntity orderRecordEntity
	) {
		return domainList.stream()
			.map(domain -> toEntity(domain, orderRecordEntity))
			.collect(Collectors.toList());
	}

}
