package store.nightmarket.persistence.persistitem.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "product_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostEntity extends BaseUuidEntity {

	@Embedded
	private RatingEntity ratingEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductEntity productEntity;

	@OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
	private List<ProductPostImageManagerEntity> productPostImageManagerEntityList = new ArrayList<>();

	@OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
	private List<ReviewEntity> reviewEntityList = new ArrayList<>();

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	private ProductPostEntity(
		UUID id,
		RatingEntity ratingEntity,
		ProductEntity productEntity,
		boolean deleted
	) {
		super(id);
		this.ratingEntity = ratingEntity;
		this.productEntity = productEntity;
		this.deleted = deleted;
	}

	public static ProductPostEntity newInstance(
		UUID id,
		RatingEntity ratingEntity,
		ProductEntity productEntity,
		boolean deleted
	) {
		return new ProductPostEntity(
			id,
			ratingEntity,
			productEntity,
			deleted
		);
	}

}
