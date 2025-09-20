package store.nightmarket.persistence.persistitem.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
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
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "product_post")
@DiscriminatorValue("PRODUCT_POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostEntity extends ImageOwnerModelEntity {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductEntity productEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@Embedded
	private RatingEntity ratingEntity;

	@OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
	private List<ReviewEntity> reviewEntityList = new ArrayList<>();

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	private ProductPostEntity(
		UUID id,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		boolean deleted
	) {
		super(id);
		this.productEntity = productEntity;
		this.userEntity = userEntity;
		this.ratingEntity = ratingEntity;
		this.deleted = deleted;
	}

	public static ProductPostEntity newInstance(
		UUID id,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		boolean deleted
	) {
		return new ProductPostEntity(
			id,
			productEntity,
			userEntity,
			ratingEntity,
			deleted
		);
	}

}
