package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class OptionGroupMapper {

	public static OptionGroup toDomain(OptionGroupEntity entity) {
		return OptionGroup.newInstanceWithCreatedAt(
			new OptionGroupId(entity.getProductId()),
			entity.getCreatedAt(),
			new ProductId(entity.getProductId()),
			new UserId(entity.getUserId()),
			new Name(entity.getName().getValue()),
			entity.getDisplayOrder()
		);
	}

	public static OptionGroupEntity toEntity(OptionGroup domain) {
		return OptionGroupEntity.newInstanceWithCreatedAt(
			domain.getOptionGroupId().getId(),
			domain.getCreatedAt(),
			domain.getProductId().getId(),
			domain.getUserId().getId(),
			new NameEntity(domain.getName().getValue()),
			domain.getOrder()
		);
	}

}
