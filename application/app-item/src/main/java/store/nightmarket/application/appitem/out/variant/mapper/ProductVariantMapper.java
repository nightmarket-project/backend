package store.nightmarket.application.appitem.out.variant.mapper;

import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

public class ProductVariantMapper {

	public static ProductVariant toDomain(ProductVariantEntity entity) {
		return ProductVariant.newInstanceWithCreatedAt(
			new ProductVariantId(entity.getId()),
			entity.getCreatedAt(),
			new ProductId(entity.getProductId()),
			new UserId(entity.getUserId()),
			entity.getSKUCode(),
			new Quantity(entity.getQuantityEntity().getAmount())
		);
	}

	public static ProductVariantEntity toEntity(ProductVariant domain) {
		return ProductVariantEntity.newInstanceWithCreatedAt(
			domain.getProductVariantId().getId(),
			domain.getCreatedAt(),
			domain.getProductId().getId(),
			domain.getSeller().getId(),
			domain.getSKUCode(),
			new QuantityEntity(domain.getQuantity().value())
		);
	}

}
