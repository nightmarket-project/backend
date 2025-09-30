package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class OptionGroupMapper {

	public static OptionGroup toDomain(OptionGroupEntity entity) {
		return OptionGroup.newInstanceWithCreatedAt(
			new OptionGroupId(entity.getProductId()),
			entity.getCreatedAt(),
			new ProductId(entity.getProductId()),
			new Name(entity.getName().getValue()),
			entity.getDisplayOrder()
		);
	}

	public static OptionGroupEntity toEntity(OptionGroup domain) {
		return OptionGroupEntity.newInstanceWithCreatedAt(
			domain.getOptionGroupId().getId(),
			domain.getCreatedAt(),
			domain.getProductId().getId(),
			new NameEntity(domain.getName().getValue()),
			domain.getOrder()
		);
	}

}
