package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

public class ReviewMapper {

    public static Review toDomain(ReviewEntity entity) {
        return Review.newInstance(
            new ReviewId(entity.getId()),
            new ProductPostId(entity.getProductPostEntity().getId()),
            new UserId(entity.getUserId()),
            CommentTextMapper.toDomain(entity.getCommentText()),
            ImageMapper.toDomain(entity.getImageEntity()),
            RatingMapper.toDomain(entity.getRating())
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
            RatingMapper.toEntity(domain.getRating()),
            domain.getCreatedAt(),
            domain.isDeleted(),
            productPostEntity
        );
    }

}
