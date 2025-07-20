package store.nightmarket.application.appitem.mapper;

import store.nightmarket.persistence.persistitem.entity.valueobject.Price;

public class PriceMapper {

    public static store.nightmarket.domain.item.valueobject.Price toDomain(Price price) {
        return new store.nightmarket.domain.item.valueobject.Price(price.getAmount());
    }
    
    public static Price toEntity(store.nightmarket.domain.item.valueobject.Price price) {
        return new Price(price.amount());
    }

}
