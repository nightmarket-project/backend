package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.model.id.ImageManagerId;
import store.nightmarket.itemweb.model.id.ImageOwnerId;
import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;
import store.nightmarket.persistence.persistitem.entity.model.ImageOwnerModelEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

public class ImageManagerMapper {

	public static ImageManager toDomain(ImageManagerEntity entity) {
		return ImageManager.newInstanceWithCreatedAt(
			new ImageManagerId(entity.getId()),
			entity.getCreatedAt(),
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
		return ImageManagerEntity.newInstanceWithCreatedAt(
			domain.getImageManagerId().getId(),
			domain.getCreatedAt(),
			new ImageEntity(domain.getImage().imageUrl()),
			ImageTypeMapper.toEntity(domain.getImageType()),
			domain.getDisplayOrder(),
			imageOwnerModelEntity
		);
	}

}
