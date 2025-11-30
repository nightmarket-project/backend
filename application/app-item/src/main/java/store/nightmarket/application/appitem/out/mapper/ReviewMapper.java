package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.model.id.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ReviewMapper {

	public static Review toDomain(ReviewEntity entity) {
		return Review.newInstanceWithCreatedAt(
			new ReviewId(entity.getId()),
			entity.getCreatedAt(),
			new ProductPostId(entity.getProductPostEntity().getId()),
			new UserId(entity.getUserEntity().getId()),
			new CommentText(entity.getCommentTextEntity().getValue()),
			new Rating(entity.getRatingEntity().getAmount())
		);
	}

	public static ReviewEntity toEntity(
		Review domain,
		ProductPostEntity productPostEntity,
		UserEntity userEntity
	) {
		return ReviewEntity.newInstanceWithCreatedAt(
			domain.getReviewId().getId(),
			domain.getCreatedAt(),
			new CommentTextEntity(domain.getCommentText().getValue()),
			new RatingEntity(domain.getRating().value()),
			domain.isDeleted(),
			productPostEntity,
			userEntity
		);
	}

}
