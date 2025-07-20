package store.nightmarket.application.appitem.mapper;


import store.nightmarket.persistence.persistitem.entity.valueobject.Name;

public class NameMapper {

    public static store.nightmarket.domain.item.valueobject.Name toDomain(Name name) {
        return new store.nightmarket.domain.item.valueobject.Name(name.getValue());
    }

    public static Name toEntity(store.nightmarket.domain.item.valueobject.Name name) {
        return new Name(name.getValue());
    }

}
