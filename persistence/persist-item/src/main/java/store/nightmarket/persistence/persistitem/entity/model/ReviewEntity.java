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

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@Embedded
	@Column(name = "text")
	private CommentTextEntity commentTextEntity;

	@Embedded
	@Column(name = "rating", nullable = false)
	private RatingEntity ratingEntity;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	@OneToOne(mappedBy = "reviewEntity")
	private ReplyEntity replyEntity;

	public ReviewEntity(
		UUID id,
		UUID userId,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		boolean deleted,
		ProductPostEntity productPostEntity,
		ReplyEntity replyEntity
	) {
		super(id);
		this.userId = userId;
		this.commentTextEntity = commentTextEntity;
		this.ratingEntity = ratingEntity;
		this.deleted = deleted;
		this.productPostEntity = productPostEntity;
		this.replyEntity = replyEntity;
	}

	public static ReviewEntity newInstance(
		UUID id,
		UUID userId,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		boolean deleted,
		ProductPostEntity productPostEntity,
		ReplyEntity replyEntity
	) {
		return new ReviewEntity(
			id,
			userId,
			commentTextEntity,
			ratingEntity,
			deleted,
			productPostEntity,
			replyEntity
		);
	}

}
