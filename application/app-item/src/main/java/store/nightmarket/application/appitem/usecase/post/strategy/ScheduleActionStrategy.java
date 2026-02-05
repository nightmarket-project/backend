package store.nightmarket.application.appitem.usecase.post.strategy;

import store.nightmarket.application.appitem.usecase.post.strategy.payload.ScheduleActionPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

public interface ScheduleActionStrategy<P extends ScheduleActionPayload> {
	ScheduleActionType getType();

	Class<P> payloadClass();

	boolean requiresPayload();

	void execute(SchedulePost schedulePost, P payload);
}
