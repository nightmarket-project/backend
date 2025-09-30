package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ReplyEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;

public class ReplyMapper {

	public static Reply toDomain(ReplyEntity entity) {
		return Reply.newInstanceWithCreatedAt(
			new ReplyId(entity.getId()),
			entity.getCreatedAt(),
			new CommentText(entity.getCommentTextEntity().getValue()),
			new UserId(entity.getUserEntity().getId()),
			new ReviewId(entity.getReviewEntity().getId())
		);
	}

	public static ReplyEntity toEntity(
		Reply domain,
		ReviewEntity reviewEntity,
		UserEntity userEntity
	) {
		return ReplyEntity.newInstanceWithCreatedAt(
			domain.getReplyId().getId(),
			domain.getCreatedAt(),
			new CommentTextEntity(domain.getCommentText().getValue()),
			domain.isDeleted(),
			userEntity,
			reviewEntity
		);
	}

}
