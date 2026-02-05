package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.itemweb.exception.SchedulePostException;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@Getter
public class SchedulePost extends BaseModel<SchedulePostId> {

	private ProductPostId productPostId;
	private LocalDateTime scheduledAt;
	private SchedulePostState state;
	private ScheduleActionType type;
	private String context;

	private SchedulePost(
		SchedulePostId id,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type,
		String context
	) {
		super(id);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
		this.type = type;
		this.context = context;
	}

	private SchedulePost(
		SchedulePostId id,
		LocalDateTime createdAt,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type,
		String context
	) {
		super(id, createdAt);
		this.productPostId = productPostId;
		this.scheduledAt = scheduledAt;
		this.state = state;
		this.type = type;
		this.context = context;
	}

	public static SchedulePost newInstance(
		SchedulePostId id,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type,
		String context
	) {
		return new SchedulePost(
			id,
			productPostId,
			scheduledAt,
			state,
			type,
			context
		);
	}

	public static SchedulePost newInstanceWithCreatedAt(
		SchedulePostId id,
		LocalDateTime createdAt,
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		SchedulePostState state,
		ScheduleActionType type,
		String context
	) {
		return new SchedulePost(
			id,
			createdAt,
			productPostId,
			scheduledAt,
			state,
			type,
			context
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

	public boolean isDone() {
		return state.equals(SchedulePostState.DONE);
	}

}
