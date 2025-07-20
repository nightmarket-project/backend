package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;

public class ProductVariantMapper {

    public static ProductVariant toDomain(ProductVariantEntity entity) {
        return ProductVariant.newInstance(
            new ProductVariantId(entity.getId()),
            new ProductId(entity.getProductEntity().getId()),
            new UserId(entity.getUserId()),
            entity.getSKUCode(),
            QuantityMapper.toDomain(entity.getQuantity())
        );
    }

    public static ProductVariantEntity toEntity(
        ProductVariant domain,
        ProductEntity productEntity
    ) {
        return ProductVariantEntity.newInstance(
            domain.getProductVariantId().getId(),
            domain.getProductId().getId(),
            domain.getSKUCode(),
            QuantityMapper.toEntity(domain.getQuantity()),
            productEntity
        );
    }

}
