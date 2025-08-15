package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;

public class ImageTypeMapper {

	public static DomainImageType toDomain(EntityImageType entityImageType) {
		if (entityImageType == null) {
			return null;
		}

		return switch (entityImageType) {
			case MAIN -> DomainImageType.MAIN;
			case DETAIL -> DomainImageType.DETAIL;
			case THUMBNAIL -> DomainImageType.THUMBNAIL;
		};
	}

	public static EntityImageType toEntity(DomainImageType domainImageType) {
		if (domainImageType == null) {
			return null;
		}
		return switch (domainImageType) {
			case MAIN -> EntityImageType.MAIN;
			case DETAIL -> EntityImageType.DETAIL;
			case THUMBNAIL -> EntityImageType.THUMBNAIL;
		};
	}

}
