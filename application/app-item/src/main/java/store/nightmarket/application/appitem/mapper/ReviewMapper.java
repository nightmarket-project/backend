package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReplyEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ReviewMapper {

	public static Review toDomain(ReviewEntity entity) {
		return Review.newInstance(
			new ReviewId(entity.getId()),
			new ProductPostId(entity.getProductPostEntity().getId()),
			new UserId(entity.getUserId()),
			new CommentText(
				entity.getCommentTextEntity().getValue(),
				entity.getCommentTextEntity().isDeleted()
			),
			new Rating(entity.getRatingEntity().getValue())
		);
	}

	public static ReviewEntity toEntity(
		Review domain,
		ProductPostEntity productPostEntity,
		ReplyEntity replyEntity
	) {
		return ReviewEntity.newInstance(
			domain.getReviewId().getId(),
			domain.getAuthor().getId(),
			new CommentTextEntity(
				domain.getCommentText().getValue(),
				domain.getCommentText().isDeleted()
			),
			new RatingEntity(domain.getRating().value()),
			domain.getCreatedAt(),
			domain.isDeleted(),
			productPostEntity,
			replyEntity
		);
	}

}
