package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

public class ProductVariantMapper {

	public static ProductVariant toDomain(ProductVariantEntity entity) {
		return ProductVariant.newInstance(
			new ProductVariantId(entity.getId()),
			new ProductId(entity.getProductId()),
			new UserId(entity.getUserId()),
			entity.getSKUCode(),
			new Quantity(entity.getQuantityEntity().getValue())
		);
	}

	public static ProductVariantEntity toEntity(ProductVariant domain) {
		return ProductVariantEntity.newInstance(
			domain.getProductVariantId().getId(),
			domain.getProductId().getId(),
			domain.getSeller().getId(),
			domain.getSKUCode(),
			new QuantityEntity(domain.getQuantity().value())
		);
	}
	
}
