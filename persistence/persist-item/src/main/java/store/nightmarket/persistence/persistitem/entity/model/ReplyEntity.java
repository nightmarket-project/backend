package store.nightmarket.persistence.persistitem.entity.model;

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

@Getter
@Entity
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "content", nullable = false)
	private CommentTextEntity commentTextEntity;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private UserEntity userEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", nullable = false)
	private ReviewEntity reviewEntity;

	public ReplyEntity(
		UUID id,
		CommentTextEntity commentTextEntity,
		boolean deleted,
		UserEntity userEntity,
		ReviewEntity reviewEntity
	) {
		super(id);
		this.commentTextEntity = commentTextEntity;
		this.deleted = deleted;
		this.userEntity = userEntity;
		this.reviewEntity = reviewEntity;
	}

	public static ReplyEntity newInstance(
		UUID id,
		CommentTextEntity commentTextEntity,
		boolean deleted,
		UserEntity userEntity,
		ReviewEntity reviewEntity
	) {
		return new ReplyEntity(
			id,
			commentTextEntity,
			deleted,
			userEntity,
			reviewEntity
		);
	}

}
