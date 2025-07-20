package store.nightmarket.application.appitem.mapper;

import store.nightmarket.persistence.persistitem.entity.valueobject.Quantity;

public class QuantityMapper {

    public static store.nightmarket.domain.item.valueobject.Quantity toDomain(Quantity entity) {
        return new store.nightmarket.domain.item.valueobject.Quantity(entity.getValue());
    }

    public static Quantity toEntity(store.nightmarket.domain.item.valueobject.Quantity domain) {
        return new Quantity(domain.value());
    }

}
