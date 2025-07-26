package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

public class QuantityMapper {

    public static Quantity toDomain(QuantityEntity entity) {
        return new Quantity(entity.getValue());
    }

    public static QuantityEntity toEntity(Quantity domain) {
        return new QuantityEntity(domain.value());
    }

}
