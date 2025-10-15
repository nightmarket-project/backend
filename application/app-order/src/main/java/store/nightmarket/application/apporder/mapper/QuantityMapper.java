package store.nightmarket.application.apporder.mapper;

import store.nightmarket.domain.order.valueobject.Quantity;
import store.nightmarket.persistence.persistorder.entity.valueobject.QuantityEntity;

public class QuantityMapper {

	public static Quantity toDomain(QuantityEntity entity) {
		return new Quantity(entity.getQuantity());
	}

	public static QuantityEntity toEntity(Quantity domain) {
		return new QuantityEntity(domain.getValue());
	}
}
