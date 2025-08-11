package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

public class ReviewMapper {

	public static Review toDomain(ReviewEntity entity) {
		return Review.newInstance(
			new ReviewId(entity.getId()),
			new ProductPostId(entity.getProductPostEntity().getId()),
			new UserId(entity.getUserId()),
			CommentTextMapper.toDomain(entity.getCommentTextEntity()),
			ImageManagerMapper.toDomain(entity.getImageManagerEntity()),
			new Rating(entity.getRatingEntity().getValue())
		);
	}

	public static ReviewEntity toEntity(
		Review domain,
		ProductPostEntity productPostEntity
	) {
		return ReviewEntity.newInstance(
			domain.getReviewId().getId(),
			domain.getAuthor().getId(),
			CommentTextMapper.toEntity(domain.getCommentText()),
			new RatingEntity(domain.getRating().value()),
			domain.getCreatedAt(),
			domain.isDeleted(),
			productPostEntity
		);
	}

}
