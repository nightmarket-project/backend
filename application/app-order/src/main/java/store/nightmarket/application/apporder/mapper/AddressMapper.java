package store.nightmarket.application.apporder.mapper;

import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.persistence.persistorder.entity.valueobject.AddressEntity;

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
