package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "review")
@DiscriminatorValue("REVIEW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends ImageOwnerModelEntity {

	@Embedded
	private CommentTextEntity commentTextEntity;

	@Embedded
	private RatingEntity ratingEntity;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private UserEntity userEntity;

	@OneToOne(mappedBy = "reviewEntity")
	private ReplyEntity replyEntity;

	public ReviewEntity(
		UUID id,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		boolean deleted,
		ProductPostEntity productPostEntity,
		UserEntity userEntity
	) {
		super(id);
		this.commentTextEntity = commentTextEntity;
		this.ratingEntity = ratingEntity;
		this.deleted = deleted;
		this.productPostEntity = productPostEntity;
		this.userEntity = userEntity;
	}

	public static ReviewEntity newInstance(
		UUID id,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		boolean deleted,
		ProductPostEntity productPostEntity,
		UserEntity userEntity
	) {
		return new ReviewEntity(
			id,
			commentTextEntity,
			ratingEntity,
			deleted,
			productPostEntity,
			userEntity
		);
	}

}
