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
        return VariantOptionValue.newInstance(
            new VariantOptionValueId(entity.getId()),
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
        return VariantOptionValueEntity.newInstance(
            domain.getVariantOptionValueId().getId(),
            productVariantEntity,
            optionGroupEntity,
            optionValueEntity
        );
    }

}
