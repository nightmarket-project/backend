package store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

public class SchedulePostStrategyExecutorDto {

	@Builder
	public record Input(
		SchedulePostId schedulePostId
	) {

	}

}
