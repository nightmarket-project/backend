package store.nightmarket.application.appitem.mapper;

import java.util.List;

import store.nightmarket.itemweb.model.Image;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ProductPostMapper {

	public static ProductPost toDomain(ProductPostEntity entity) {
		List<Image> imageList = entity.getImageEntityList().stream()
			.map(ImageMapper::toDomain)
			.toList();

		return ProductPost.newInstance(
			new ProductPostId(entity.getId()),
			ProductMapper.toDomain(entity.getProductEntity()),
			new Rating(entity.getRatingEntity().getValue()),
			imageList
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
