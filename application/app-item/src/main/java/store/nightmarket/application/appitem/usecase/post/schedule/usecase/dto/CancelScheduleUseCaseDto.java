package store.nightmarket.application.appitem.usecase.post.schedule.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

public class CancelScheduleUseCaseDto {

	@Builder
	public record Input(
		SchedulePostId schedulePostId
	) {

	}

}
