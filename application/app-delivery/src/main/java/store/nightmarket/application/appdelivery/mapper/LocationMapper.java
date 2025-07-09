package store.nightmarket.application.appdelivery.mapper;

import store.nightmarket.domain.delivery.valueobject.Location;
import store.nightmarket.persistence.persistdelivery.entity.valueobject.LocationEntity;

public class LocationMapper {

	public static Location toDomain(LocationEntity entity) {
		return new Location(entity.getName());
	}

	public static LocationEntity toEntity(Location domain) {
		return new LocationEntity(domain.getName());
	}

}