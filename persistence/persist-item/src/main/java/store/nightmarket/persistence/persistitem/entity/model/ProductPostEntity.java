package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.domain.itemweb.model.state.PostState;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private PostState state;

	@Column(name = "publish_at")
	private LocalDateTime publishAt;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	private ProductPostEntity(
		UUID id,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		super(id);
		this.productEntity = productEntity;
		this.userEntity = userEntity;
		this.ratingEntity = ratingEntity;
		this.state = state;
		this.publishAt = publishAt;
		this.expiredAt = expiredAt;
	}

	private ProductPostEntity(
		UUID id,
		LocalDateTime createdAt,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		super(id, createdAt);
		this.productEntity = productEntity;
		this.userEntity = userEntity;
		this.ratingEntity = ratingEntity;
		this.state = state;
		this.publishAt = publishAt;
		this.expiredAt = expiredAt;
	}

	public static ProductPostEntity newInstance(
		UUID id,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		return new ProductPostEntity(
			id,
			productEntity,
			userEntity,
			ratingEntity,
			state,
			publishAt,
			expiredAt
		);
	}

	public static ProductPostEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		ProductEntity productEntity,
		UserEntity userEntity,
		RatingEntity ratingEntity,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		return new ProductPostEntity(
			id,
			createdAt,
			productEntity,
			userEntity,
			ratingEntity,
			state,
			publishAt,
			expiredAt
		);
	}

}
