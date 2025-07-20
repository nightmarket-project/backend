package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;

public class OptionGroupMapper {

    public static OptionGroup toDomain(OptionGroupEntity entity) {
        return OptionGroup.newInstance(
            new OptionGroupId(entity.getId()),
            new ProductId(entity.getProductEntity().getId()),
            NameMapper.toDomain(entity.getName()),
            entity.getOrder()
        );
    }

    public static OptionGroupEntity toEntity(
        OptionGroup domain,
        ProductEntity productEntity
    ) {
        return OptionGroupEntity.newInstance(
            domain.getOptionGroupId().getId(),
            NameMapper.toEntity(domain.getName()),
            domain.getOrder(),
            productEntity
        );
    }

}
