package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.persistence.persistitem.entity.model.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

public class ImageManagerMapper {

	public static ImageManager toDomain(ImageManagerEntity entity) {
		return ImageManager.newInstance(
			new ImageManagerId(entity.getId()),
			new ImageId(entity.getImageEntity().getId()),
			entity.getDisplayOrder(),
			entity.getType()
		);
	}

	public static ImageManagerEntity toEntity(
		ImageManager domain,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity,
		ProductPostEntity productPostEntity
	) {
		return ImageManagerEntity.newInstance(
			domain.getId().getId(),
			domain.getDisplayOrder(),
			domain.getImageType(),
			imageEntity,
			reviewEntity,
			productPostEntity
		);
	}

}
