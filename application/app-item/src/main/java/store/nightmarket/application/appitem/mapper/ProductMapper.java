package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        return Product.newInstance(
            new ProductId(entity.getId()),
            new Name(entity.getNameEntity().getValue()),
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
            new NameEntity(domain.getName().getValue()),
            domain.getDescription(),
            PriceMapper.toEntity(domain.getPrice()),
            productPostEntity
        );
    }

}
