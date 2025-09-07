package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ProductPostMapper {

	public static ProductPost toDomain(ProductPostEntity entity) {
		return ProductPost.newInstance(
			new ProductPostId(entity.getId()),
			new ProductId(entity.getProductEntity().getId()),
			new Rating(entity.getRatingEntity().getValue()),
			entity.isDeleted()
		);
	}

	public static ProductPostEntity toEntity(
		ProductPost domain,
		ProductEntity productEntity,
		UserEntity userEntity
	) {
		return ProductPostEntity.newInstance(
			domain.getProductId().getId(),
			productEntity,
			userEntity,
			new RatingEntity(domain.getRating().value()),
			domain.isDeleted()
		);
	}

}
