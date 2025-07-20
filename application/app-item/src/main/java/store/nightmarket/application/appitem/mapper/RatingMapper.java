package store.nightmarket.application.appitem.mapper;

import store.nightmarket.persistence.persistitem.entity.valueobject.Rating;

public class RatingMapper {

    public static store.nightmarket.itemweb.valueobject.Rating toDomain(Rating entity) {
        return new store.nightmarket.itemweb.valueobject.Rating(entity.getValue());
    }

    public static Rating toEntity(store.nightmarket.itemweb.valueobject.Rating domain) {
        return new Rating(domain.value());
    }

}
