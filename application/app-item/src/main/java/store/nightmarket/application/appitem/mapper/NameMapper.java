package store.nightmarket.application.appitem.mapper;


import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class NameMapper {

    public static Name toDomain(NameEntity nameEntity) {
        return new Name(nameEntity.getValue());
    }

    public static NameEntity toEntity(Name name) {
        return new NameEntity(name.getValue());
    }

}
