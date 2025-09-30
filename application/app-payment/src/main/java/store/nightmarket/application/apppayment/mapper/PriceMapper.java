package store.nightmarket.application.apppayment.mapper;

import store.nightmarket.domain.payment.valueobject.Price;
import store.nightmarket.persistence.persistpayment.entity.valueobject.PriceEntity;

public class PriceMapper {

	public static Price toDomain(PriceEntity entity) {
		return new Price(entity.getPrice());
	}

	public static PriceEntity toEntity(Price domain) {
		return new PriceEntity(domain.getPrice());
	}

}
