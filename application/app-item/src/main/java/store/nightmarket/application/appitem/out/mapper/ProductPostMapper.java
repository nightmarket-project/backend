package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ProductPostMapper {

	public static ProductPost toDomain(ProductPostEntity entity) {
		return ProductPost.newInstanceWithCreatedAt(
			new ProductPostId(entity.getId()),
			entity.getCreatedAt(),
			new ProductId(entity.getProductEntity().getId()),
			new Rating(entity.getRatingEntity().getAmount()),
			entity.getState()
		);
	}

	public static ProductPostEntity toEntity(
		ProductPost domain,
		ProductEntity productEntity
	) {
		return ProductPostEntity.newInstanceWithCreatedAt(
			domain.getProductPostId().getId(),
			domain.getCreatedAt(),
			productEntity,
			new RatingEntity(domain.getRating().value()),
			domain.getState()
		);
	}

}
