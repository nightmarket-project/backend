package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.UnpublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

@Component
@RequiredArgsConstructor
public class UnpublishSchedulePostStrategy implements SchedulePostActionStrategy {

	private final UnpublishProductPostUseCase unpublishProductPostUseCase;

	@Override
	public SchedulePostActionType getType() {
		return SchedulePostActionType.UNPUBLISH;
	}

	@Override
	public void execute(SchedulePost schedulePost) {
		unpublishProductPostUseCase.execute(
			UnpublishProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}