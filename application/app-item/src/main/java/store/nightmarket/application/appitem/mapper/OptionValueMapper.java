package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

public class OptionValueMapper {

	public static OptionValue toDomain(OptionValueEntity entity) {
		return OptionValue.newInstanceWithCreatedAt(
			new OptionValueId(entity.getId()),
			entity.getCreatedAt(),
			new OptionGroupId(entity.getOptionGroupEntity().getId()),
			entity.getName(),
			new Price(entity.getPriceEntity().getAmount()),
			entity.getDisplayOrder()
		);
	}

	public static OptionValueEntity toEntity(
		OptionValue domain,
		OptionGroupEntity optionGroupEntity
	) {
		return OptionValueEntity.newInstanceWithCreatedAt(
			domain.getOptionValueId().getId(),
			domain.getCreatedAt(),
			domain.getValue(),
			new PriceEntity(domain.getPrice().amount()),
			domain.getOrder(),
			optionGroupEntity
		);
	}

}
