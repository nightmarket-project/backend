package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
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
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentTextEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends BaseUuidEntity {

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@Embedded
	@Column(name = "text")
	private CommentTextEntity commentTextEntity;

	@Embedded
	@Column(name = "rating", nullable = false)
	private RatingEntity ratingEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private ImageEntity imageEntity;

	@Column(name = "create_date", nullable = false)
	private LocalDate createDate;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	@OneToOne(mappedBy = "reviewEntity", fetch = FetchType.EAGER)
	private ReplyEntity replyEntity;

	public ReviewEntity(
		UUID userId,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		ImageEntity imageEntity,
		LocalDate createDate,
		boolean deleted,
		ProductPostEntity productPostEntity,
		ReplyEntity replyEntity
	) {
		this.userId = userId;
		this.commentTextEntity = commentTextEntity;
		this.ratingEntity = ratingEntity;
		this.imageEntity = imageEntity;
		this.createDate = createDate;
		this.deleted = deleted;
		this.productPostEntity = productPostEntity;
		this.replyEntity = replyEntity;
	}

	public static ReviewEntity newInstance(
		UUID userId,
		CommentTextEntity commentTextEntity,
		RatingEntity ratingEntity,
		ImageEntity imageEntity,
		LocalDate createDate,
		boolean deleted,
		ProductPostEntity productPostEntity,
		ReplyEntity replyEntity
	) {
		return new ReviewEntity(
			userId,
			commentTextEntity,
			ratingEntity,
			imageEntity,
			createDate,
			deleted,
			productPostEntity,
			replyEntity
		);
	}

}
