package store.nightmarket.application.appitem.schedule.event;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

@Builder
public record SchedulePostCanceledEvent(
	SchedulePostId schedulePostId
) {

}
