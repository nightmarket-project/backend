package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class OptionGroupMapper {

	public static OptionGroup toDomain(OptionGroupEntity entity) {
		return OptionGroup.newInstance(
			new OptionGroupId(entity.getProductId()),
			new ProductId(entity.getProductId()),
			new Name(entity.getName().getValue()),
			entity.getDisplayOrder()
		);
	}

	public static OptionGroupEntity toEntity(OptionGroup domain) {
		return OptionGroupEntity.newInstance(
			domain.getOptionGroupId().getId(),
			domain.getProductId().getId(),
			new NameEntity(domain.getName().getValue()),
			domain.getOrder()
		);
	}

}
