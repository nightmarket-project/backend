package store.nightmarket.application.apporder.mapper;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;

public class DetailOrderRecordMapper {

	public static DetailOrderRecord toDomain(DetailOrderRecordEntity entity) {
		return DetailOrderRecord.newInstance(
			new DetailOrderRecordId(entity.getId()),
			new ProductId(entity.getProductId()),
			QuantityMapper.toDomain(entity.getQuantity()),
			entity.getState()
		);
	}

	public static DetailOrderRecordEntity toEntity(DetailOrderRecord domain) {
		return new DetailOrderRecordEntity(
			domain.getProductId().getId(),
			QuantityMapper.toEntity(domain.getQuantity()),
			domain.getState()
		);
	}

}
