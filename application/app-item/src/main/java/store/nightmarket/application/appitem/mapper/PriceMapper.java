package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

public class PriceMapper {

    public static Price toDomain(PriceEntity priceEntity) {
        return new Price(priceEntity.getAmount());
    }

    public static PriceEntity toEntity(Price price) {
        return new PriceEntity(price.amount());
    }

}
