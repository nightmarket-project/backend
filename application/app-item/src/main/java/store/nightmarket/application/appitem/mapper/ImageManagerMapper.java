package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;
import store.nightmarket.persistence.persistitem.entity.model.ImageOwnerModelEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

public class ImageManagerMapper {

	public static ImageManager toDomain(ImageManagerEntity entity) {
		return ImageManager.newInstance(
			new ImageManagerId(entity.getId()),
			new Image(entity.getImageEntity().getUrl()),
			ImageTypeMapper.toDomain(entity.getEntityImageType()),
			entity.getDisplayOrder(),
			new ImageOwnerId(entity.getImageOwnerModelEntity().getId())
		);
	}

	public static ImageManagerEntity toEntity(
		ImageManager domain,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		return ImageManagerEntity.newInstance(
			domain.getImageManagerId().getId(),
			new ImageEntity(domain.getImage().imageUrl()),
			ImageTypeMapper.toEntity(domain.getDomainImageType()),
			domain.getDisplayOrder(),
			imageOwnerModelEntity
		);
	}
	
}
