package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.ProductPostImageManager;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.entity.model.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostImageManagerEntity;

public class ProductPostImageMapper {

	public static ProductPostImageManager toDomain(ProductPostImageManagerEntity entity) {
		return ProductPostImageManager.newInstance(
			new ImageManagerId(entity.getId()),
			new ImageId(entity.getImageEntity().getId()),
			entity.getDisplayOrder(),
			entity.getImageType(),
			new ProductPostId(entity.getProductPostEntity().getId())
		);
	}

	public static ProductPostImageManagerEntity toEntity(
		ProductPostImageManager domain,
		ImageEntity imageEntity,
		ProductPostEntity productPostEntity
	) {
		return ProductPostImageManagerEntity.newInstance(
			domain.getId().getId(),
			domain.getDisplayOrder(),
			domain.getImageType(),
			imageEntity,
			productPostEntity
		);
	}
	
}
