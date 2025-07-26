package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;

public class ProductPostMapper {

    public static ProductPost toDomain(ProductPostEntity entity) {
        return ProductPost.newInstance(
            new ProductPostId(entity.getId()),
            ProductMapper.toDomain(entity.getProductEntity()),
            RatingMapper.toDomain(entity.getRatingEntity()),
            ImageMapper.toDomainList(entity.getImageEntityList())
        );
    }

    public static ProductPostEntity toEntity(ProductPost domain) {
        return ProductPostEntity.newInstance(
            domain.getProductPostId().getId(),
            RatingMapper.toEntity(domain.getRating()),
            domain.isDeleted()
        );
    }

}
