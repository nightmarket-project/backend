package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@Getter
@Entity
@Table(name = "schedule_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulePostEntity extends BaseUuidEntity {

	@Column(name = "product_post_id", nullable = false)
	private UUID productPostId;

	@Column(name = "scheduled_at", nullable = false)
	private LocalDateTime scheduledAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private SchedulePostState state;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ScheduleActionType type;

	private SchedulePostEntity(
		UUID id,
		UUID productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type
	) {
		super(id);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
		this.type = type;
	}

	private SchedulePostEntity(
		UUID id,
		LocalDateTime createdAt,
		UUID productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type
	) {
		super(id, createdAt);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
		this.type = type;
	}

	public static SchedulePostEntity newInstance(
		UUID id,
		UUID productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type
	) {
		return new SchedulePostEntity(
			id,
			productPostId,
			scheduledAt,
			state,
			type
		);
	}

	public static SchedulePostEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		UUID productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type
	) {
		return new SchedulePostEntity(
			id,
			createdAt,
			productPostId,
			scheduledAt,
			state,
			type
		);
	}

}
