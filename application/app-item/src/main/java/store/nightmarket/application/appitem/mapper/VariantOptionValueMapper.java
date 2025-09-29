package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

public class VariantOptionValueMapper {

	public static VariantOptionValue toDomain(VariantOptionValueEntity entity) {
		return VariantOptionValue.newInstanceWithCreatedAt(
			new VariantOptionValueId(entity.getId()),
			entity.getCreatedAt(),
			new ProductVariantId(entity.getProductVariantEntity().getId()),
			new OptionGroupId(entity.getOptionGroupEntity().getId()),
			new OptionValueId(entity.getOptionValueEntity().getId())
		);
	}

	public static VariantOptionValueEntity toEntity(
		VariantOptionValue domain,
		ProductVariantEntity productVariantEntity,
		OptionGroupEntity optionGroupEntity,
		OptionValueEntity optionValueEntity
	) {
		return VariantOptionValueEntity.newInstanceWithCreatedAt(
			domain.getVariantOptionValueId().getId(),
			domain.getCreatedAt(),
			productVariantEntity,
			optionGroupEntity,
			optionValueEntity
		);
	}

}
