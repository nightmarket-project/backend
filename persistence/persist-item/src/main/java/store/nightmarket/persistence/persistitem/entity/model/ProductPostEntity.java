package store.nightmarket.persistence.persistitem.entity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "product_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostEntity extends BaseUuidEntity {

	@OneToOne(mappedBy = "productPostEntity")
	private ProductEntity productEntity;

	@Embedded
	private RatingEntity ratingEntity;

	@OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
	private List<ImageEntity> imageEntityList = new ArrayList<>();

	@OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
	private List<ReviewEntity> reviewEntityList = new ArrayList<>();

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	private ProductPostEntity(
		ProductEntity productEntity,
		RatingEntity ratingEntity,
		boolean deleted
	) {
		this.productEntity = productEntity;
		this.ratingEntity = ratingEntity;
		this.deleted = deleted;
	}

	public static ProductPostEntity newInstance(
		ProductEntity productEntity,
		RatingEntity ratingEntity,
		boolean deleted
	) {
		return new ProductPostEntity(
			productEntity,
			ratingEntity,
			deleted
		);
	}

}
