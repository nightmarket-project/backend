package store.nightmarket.application.appitem.usecase.post.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.PublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.EmptyPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@Component
@RequiredArgsConstructor
public class PublishScheduleStrategy implements ScheduleActionStrategy<EmptyPayload> {

	private final PublishProductPostUseCase publishProductPostUseCase;

	@Override
	public ScheduleActionType getType() {
		return ScheduleActionType.PUBLISH;
	}

	@Override
	public Class<EmptyPayload> payloadClass() {
		return EmptyPayload.class;
	}

	@Override
	public boolean requiresPayload() {
		return false;
	}

	@Override
	public void execute(SchedulePost schedulePost, EmptyPayload payload) {
		publishProductPostUseCase.execute(
			PublishProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}