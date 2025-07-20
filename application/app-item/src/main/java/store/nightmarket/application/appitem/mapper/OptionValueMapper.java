package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;

public class OptionValueMapper {

    public static OptionValue toDomain(OptionValueEntity entity) {
        return OptionValue.newInstance(
            new OptionValueId(entity.getId()),
            new OptionGroupId(entity.getOptionGroupEntity().getId()),
            entity.getValue(),
            PriceMapper.toDomain(entity.getPrice()),
            entity.getOrder()
        );
    }

    public static OptionValueEntity toEntity(
        OptionValue domain,
        OptionGroupEntity optionGroupEntity
    ) {
        return OptionValueEntity.newInstance(
            domain.getOptionValueId().getId(),
            domain.getValue(),
            PriceMapper.toEntity(domain.getPrice()),
            domain.getOrder(),
            optionGroupEntity
        );
    }

}
