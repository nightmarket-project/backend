package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class OptionGroupMapper {

    public static OptionGroup toDomain(OptionGroupEntity entity) {
        return OptionGroup.newInstance(
            new OptionGroupId(entity.getId()),
            new ProductId(entity.getProductEntity().getId()),
            new Name(entity.getNameEntity().getValue()),
            entity.getOrder()
        );
    }

    public static OptionGroupEntity toEntity(
        OptionGroup domain,
        ProductEntity productEntity
    ) {
        return OptionGroupEntity.newInstance(
            domain.getOptionGroupId().getId(),
            new NameEntity(domain.getName().getValue()),
            domain.getOrder(),
            productEntity
        );
    }

}
