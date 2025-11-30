package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.itemweb.model.state.ImageType;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;

public class ImageTypeMapper {

	public static ImageType toDomain(EntityImageType entityImageType) {
		if (entityImageType == null) {
			return null;
		}

		return switch (entityImageType) {
			case MAIN -> ImageType.MAIN;
			case DETAIL -> ImageType.DETAIL;
			case THUMBNAIL -> ImageType.THUMBNAIL;
		};
	}

	public static EntityImageType toEntity(ImageType imageType) {
		if (imageType == null) {
			return null;
		}
		return switch (imageType) {
			case MAIN -> EntityImageType.MAIN;
			case DETAIL -> EntityImageType.DETAIL;
			case THUMBNAIL -> EntityImageType.THUMBNAIL;
		};
	}

}
