package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.itemweb.exception.SchedulePostException;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@Getter
public class SchedulePost extends BaseModel<SchedulePostId> {

	private ProductPostId productPostId;
	private LocalDateTime scheduledAt;
	private SchedulePostState state;

	private SchedulePost(
		SchedulePostId id,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state
	) {
		super(id);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
	}

	private SchedulePost(
		SchedulePostId id,
		LocalDateTime createdAt,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state
	) {
		super(id, createdAt);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
	}

	public static SchedulePost newInstance(
		SchedulePostId id,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state
	) {
		return new SchedulePost(
			id,
			productPostId,
			scheduledAt,
			state
		);
	}

	public static SchedulePost newInstanceWithCreatedAt(
		SchedulePostId id,
		LocalDateTime createdAt,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state
	) {
		return new SchedulePost(
			id,
			createdAt,
			productPostId,
			scheduledAt,
			state
		);
	}

	public SchedulePostId getSchedulePostId() {
		return internalId();
	}

	public void done() {
		if (!state.canTransitionTo(SchedulePostState.DONE)) {
			throw new SchedulePostException("cannot change state to done");
		}
		this.state = SchedulePostState.DONE;
	}

	public void cancel() {
		if (!state.canTransitionTo(SchedulePostState.CANCELED)) {
			throw new SchedulePostException("cannot change state to canceled");
		}
		this.state = SchedulePostState.CANCELED;
	}

}
