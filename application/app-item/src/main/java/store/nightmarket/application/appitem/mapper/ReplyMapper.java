package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ReplyEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;

public class ReplyMapper {

	public static Reply toDomain(ReplyEntity entity) {
		return Reply.newInstance(
			new ReplyId(entity.getId()),
			new CommentText(
				entity.getCommentTextEntity().getValue(),
				entity.isDeleted()
			),
			new UserId(entity.getUserId()),
			new ReviewId(entity.getReviewEntity().getId()),
			entity.getCreatedAt()
		);
	}

	public static ReplyEntity toEntity(
		Reply domain,
		ReviewEntity reviewEntity
	) {
		return ReplyEntity.newInstance(
			domain.getReplyId().getId(),
			new CommentTextEntity(
				domain.getCommentText().getValue(),
				domain.getCommentText().isDeleted()
			),
			domain.getAuthorId().getId(),
			domain.isDeleted(),
			reviewEntity
		);
	}

}
