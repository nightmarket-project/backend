package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.itemweb.state.ImageType;

@Getter
@Entity
@Table(name = "image_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageManagerEntity extends BaseUuidEntity {

	@Column(name = "display_order", nullable = false)
	private int displayOrder;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ImageType type;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private ImageEntity imageEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private ReviewEntity reviewEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	private ImageManagerEntity(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity,
		ProductPostEntity productPostEntity
	) {
		super(id);
		this.displayOrder = displayOrder;
		this.type = type;
		this.imageEntity = imageEntity;
		this.reviewEntity = reviewEntity;
		this.productPostEntity = productPostEntity;
	}

	public static ImageManagerEntity newInstance(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ReviewEntity reviewEntity,
		ProductPostEntity productPostEntity
	) {
		return new ImageManagerEntity(
			id,
			displayOrder,
			type,
			imageEntity,
			reviewEntity,
			productPostEntity
		);
	}

}
