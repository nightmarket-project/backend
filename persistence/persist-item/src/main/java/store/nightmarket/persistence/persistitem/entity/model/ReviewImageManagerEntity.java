package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.itemweb.state.ImageType;

@Getter
@Entity
@Table(name = "review_image_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImageManagerEntity extends ImageManagerEntity {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private ReviewEntity reviewEntity;

	private ReviewImageManagerEntity(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity
	) {
		super(
			id,
			displayOrder,
			type,
			imageEntity
		);
		this.reviewEntity = reviewEntity;
	}

	public static ReviewImageManagerEntity newInstance(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity
	) {
		return new ReviewImageManagerEntity(
			id,
			displayOrder,
			type,
			imageEntity,
			reviewEntity
		);
	}

}
