package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        return Product.newInstance(
            new ProductId(entity.getId()),
            NameMapper.toDomain(entity.getNameEntity()),
            entity.getDescription(),
            PriceMapper.toDomain(entity.getPriceEntity())
        );
    }

    public static ProductEntity toEntity(
        Product domain,
        ProductPostEntity productPostEntity
    ) {
        return ProductEntity.newInstance(
            domain.getProductId().getId(),
            NameMapper.toEntity(domain.getName()),
            domain.getDescription(),
            PriceMapper.toEntity(domain.getPrice()),
            productPostEntity
        );
    }

}
