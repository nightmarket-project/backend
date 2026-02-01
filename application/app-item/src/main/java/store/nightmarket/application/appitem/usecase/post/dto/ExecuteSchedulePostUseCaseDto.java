package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

public class ExecuteSchedulePostUseCaseDto {

	@Builder
	public record Input(
		SchedulePostId schedulePostId
	) {

	}

}
