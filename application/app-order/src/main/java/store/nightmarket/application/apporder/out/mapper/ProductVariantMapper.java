package store.nightmarket.application.apporder.out.mapper;

import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.model.id.ProductVariantId;
import store.nightmarket.persistence.persistorder.entity.model.ProductVariantEntity;

public class ProductVariantMapper {

	public static ProductVariant toDomain(ProductVariantEntity entity) {
		return ProductVariant.newInstanceWithCreatedAt(
			new ProductVariantId(entity.getId()),
			entity.getCreatedAt(),
			PriceMapper.toDomain(entity.getPriceEntity())
		);
	}

	public static ProductVariantEntity toEntity(ProductVariant domain) {
		return new ProductVariantEntity(
			domain.getProductVariantId().getId(),
			PriceMapper.toEntity(domain.getPrice())
		);
	}

}
