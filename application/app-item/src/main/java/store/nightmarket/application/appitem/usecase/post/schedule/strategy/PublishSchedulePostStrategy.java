package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.PublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

@Component
@RequiredArgsConstructor
public class PublishSchedulePostStrategy implements SchedulePostActionStrategy {

	private final PublishProductPostUseCase publishProductPostUseCase;

	@Override
	public SchedulePostActionType getType() {
		return SchedulePostActionType.PUBLISH;
	}

	@Override
	public void execute(SchedulePost schedulePost) {
		publishProductPostUseCase.execute(
			PublishProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}