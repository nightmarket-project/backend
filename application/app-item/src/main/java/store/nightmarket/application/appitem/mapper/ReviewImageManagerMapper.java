package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.ReviewImageManager;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ReviewId;
import store.nightmarket.persistence.persistitem.entity.model.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewImageManagerEntity;

public class ReviewImageManagerMapper {

	public static ReviewImageManager toDomain(ReviewImageManagerEntity entity) {
		return ReviewImageManager.newInstance(
			new ImageManagerId(entity.getId()),
			new ImageId(entity.getImageEntity().getId()),
			entity.getDisplayOrder(),
			entity.getImageType(),
			new ReviewId(entity.getReviewEntity().getId())
		);
	}

	public static ReviewImageManagerEntity toEntity(
		ReviewImageManager domain,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity
	) {
		return ReviewImageManagerEntity.newInstance(
			domain.getId().getId(),
			domain.getDisplayOrder(),
			domain.getImageType(),
			imageEntity,
			reviewEntity
		);
	}

}
