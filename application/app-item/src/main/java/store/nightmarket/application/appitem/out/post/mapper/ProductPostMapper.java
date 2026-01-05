package store.nightmarket.application.appitem.out.post.mapper;

import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ProductPostMapper {

	public static ProductPost toDomain(ProductPostEntity entity) {
		return ProductPost.newInstanceWithCreatedAt(
			new ProductPostId(entity.getId()),
			entity.getCreatedAt(),
			new ProductId(entity.getProductEntity().getId()),
			new Rating(entity.getRatingEntity().getAmount()),
			entity.isDeleted()
		);
	}

	public static ProductPostEntity toEntity(
		ProductPost domain,
		ProductEntity productEntity,
		UserEntity userEntity
	) {
		return ProductPostEntity.newInstanceWithCreatedAt(
			domain.getProductId().getId(),
			domain.getCreatedAt(),
			productEntity,
			userEntity,
			new RatingEntity(domain.getRating().value()),
			domain.isDeleted()
		);
	}

}
