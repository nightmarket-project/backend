package store.nightmarket.application.appdelivery.mapper;

import store.nightmarket.common.out.persistence.jpa.entity.delivery.valueobject.LocationEntity;
import store.nightmarket.domain.delivery.valueobject.Location;

public class LocationMapper {

	public static Location toDomain(LocationEntity entity) {
		return new Location(entity.getName());
	}

	public static LocationEntity toEntity(Location domain) {
		return new LocationEntity(domain.getName());
	}

}