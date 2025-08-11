package store.nightmarket.application.appitem.mapper;

import java.util.List;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ProductPostMapper {

	public static ProductPost toDomain(ProductPostEntity entity) {
		List<ImageManager> imageManagerList = entity.getImageManagerEntityList().stream()
			.map(ImageManagerMapper::toDomain)
			.toList();

		return ProductPost.newInstance(
			new ProductPostId(entity.getId()),
			ProductMapper.toDomain(entity.getProductEntity()),
			new Rating(entity.getRatingEntity().getValue()),
			imageManagerList
		);
	}

	public static ProductPostEntity toEntity(ProductPost domain) {
		return ProductPostEntity.newInstance(
			domain.getProductPostId().getId(),
			new RatingEntity(domain.getRating().value()),
			domain.isDeleted()
		);
	}

}
