package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class RatingMapper {

    public static Rating toDomain(RatingEntity entity) {
        return new Rating(entity.getValue());
    }

    public static RatingEntity toEntity(Rating domain) {
        return new RatingEntity(domain.value());
    }

}
