package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

public class VariantOptionValueMapper {

    public static VariantOptionValue toDomain(VariantOptionValueEntity entity) {
        return VariantOptionValue.newInstance(
            new VariantOptionValueId(entity.getId()),
            new ProductVariantId(entity.getProductVariantEntity().getId()),
            OptionGroupMapper.toDomain(entity.getOptionGroupEntity()),
            OptionValueMapper.toDomain(entity.getOptionValueEntity())
        );
    }

    public static VariantOptionValueEntity toEntity(
        VariantOptionValue domain,
        ProductVariantEntity productVariantEntity,
        ProductEntity productEntity
    ) {
        OptionGroupEntity optionGroupEntity = OptionGroupMapper.toEntity(
            domain.getOptionGroup(),
            productEntity
        );

        return VariantOptionValueEntity.newInstance(
            domain.getVariantOptionValueId().getId(),
            productVariantEntity,
            optionGroupEntity,
            OptionValueMapper.toEntity(
                domain.getOptionValue(),
                optionGroupEntity
            )
        );
    }

}
