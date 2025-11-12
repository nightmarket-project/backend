package store.nightmarket.application.apporder.mapper;

import store.nightmarket.domain.order.valueobject.Price;
import store.nightmarket.persistence.persistorder.entity.valueobject.PriceEntity;

public class PriceMapper {

	public static Price toDomain(PriceEntity entity) {
		return new Price(entity.getPrice());
	}

	public static PriceEntity toEntity(Price domain) {
		return new PriceEntity(domain.getPrice());
	}

}
