package store.nightmarket.application.appitem.usecase.post.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

public class ScheduleProductPostUseCaseDto {

	@Builder
	public record Input(
		ProductPostId productPostId,
		LocalDateTime scheduledAt,
		ScheduleActionType type
	) {

	}

}
