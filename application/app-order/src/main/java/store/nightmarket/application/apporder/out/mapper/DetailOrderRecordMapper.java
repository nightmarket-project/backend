package store.nightmarket.application.apporder.out.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.id.DetailOrderRecordId;
import store.nightmarket.domain.order.model.id.ProductVariantId;
import store.nightmarket.persistence.persistorder.entity.model.DetailOrderRecordEntity;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public class DetailOrderRecordMapper {

	public static DetailOrderRecord toDomain(DetailOrderRecordEntity entity) {
		return DetailOrderRecord.newInstanceWithCreatedAt(
			new DetailOrderRecordId(entity.getId()),
			entity.getCreatedAt(),
			new ProductVariantId(entity.getProductVariantId()),
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
			domain.getCreatedAt(),
			domain.getProductVariantId().getId(),
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
