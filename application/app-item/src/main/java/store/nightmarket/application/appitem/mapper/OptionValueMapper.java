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
        return OptionValue.newInstance(
            new OptionValueId(entity.getId()),
            new OptionGroupId(entity.getOptionGroupEntity().getId()),
            entity.getValue(),
            new Price(entity.getPriceEntity().getAmount()),
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
            new PriceEntity(domain.getPrice().amount()),
            domain.getOrder(),
            optionGroupEntity
        );
    }

}
