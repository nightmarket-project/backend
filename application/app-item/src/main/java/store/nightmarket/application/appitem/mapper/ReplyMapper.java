package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ReplyEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

public class ReplyMapper {

    public static Reply toDomain(ReplyEntity entity) {
        return Reply.newInstance(
            new ReplyId(entity.getId()),
            CommentTextMapper.toDomain(entity.getCommentText()),
            new UserId(entity.getUserId()),
            new ReviewId(entity.getReviewEntity().getId())
        );
    }

    public static ReplyEntity toEntity(
        Reply domain,
        ReviewEntity reviewEntity
    ) {
        return ReplyEntity.newInstance(
            domain.getReplyId().getId(),
            CommentTextMapper.toEntity(domain.getCommentText()),
            domain.getAuthorId().getId(),
            domain.getCreatedAt(),
            domain.isDeleted(),
            reviewEntity
        );
    }

}
