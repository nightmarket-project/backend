package store.nightmarket.application.appdelivery.mapper;

import store.nightmarket.domain.delivery.valueobject.Address;
import store.nightmarket.persistence.persistdelivery.entity.valueobject.AddressEntity;

public class AddressMapper {

	public static Address toDomain(AddressEntity entity) {
		return new Address(
			entity.getZipCode(),
			entity.getRoadAddress(),
			entity.getDetailAddress()
		);
	}

	public static AddressEntity toEntity(Address domain) {
		return new AddressEntity(
			domain.getZipCode(),
			domain.getRoadAddress(),
			domain.getDetailAddress()
		);
	}

}