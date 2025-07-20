package store.nightmarket.application.appitem.mapper;

import store.nightmarket.persistence.persistitem.entity.valueobject.CommentText;

public class CommentTextMapper {

    public static store.nightmarket.itemweb.valueobject.CommentText toDomain(CommentText entity) {
        return new store.nightmarket.itemweb.valueobject.CommentText(
            entity.getValue(),
            entity.isDeleted()
        );
    }

    public static CommentText toEntity(store.nightmarket.itemweb.valueobject.CommentText domain) {
        return new CommentText(
            domain.getValue(),
            domain.isDeleted()
        );
    }
}
