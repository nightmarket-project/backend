package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.Image;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.persistence.persistitem.entity.model.ImageEntity;

public class ImageMapper {

	public static Image toDomain(ImageEntity entity) {
		return Image.newInstance(
			new ImageId(entity.getId()),
			entity.getUrl(),
			entity.getAltText()
		);
	}

	public static ImageEntity toEntity(Image domain) {
		return ImageEntity.newInstance(
			domain.getImageId().getId(),
			domain.getImageUrl(),
			domain.getAltText()
		);
	}

}
