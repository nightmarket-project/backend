package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

public interface SchedulePostActionStrategy {
	SchedulePostActionType getType();

	void execute(SchedulePost schedulePost);
}
