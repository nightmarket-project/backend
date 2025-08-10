package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;

public class CommentTextMapper {

    public static CommentText toDomain(CommentTextEntity entity) {
        return new CommentText(
            entity.getValue(),
            entity.isDeleted()
        );
    }

    public static CommentTextEntity toEntity(CommentText domain) {
        return new CommentTextEntity(
            domain.getValue(),
            domain.isDeleted()
        );
    }

}
