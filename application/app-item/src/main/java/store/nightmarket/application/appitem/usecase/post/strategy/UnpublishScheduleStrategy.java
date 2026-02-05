package store.nightmarket.application.appitem.usecase.post.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.UnpublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.EmptyPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@Component
@RequiredArgsConstructor
public class UnpublishScheduleStrategy implements ScheduleActionStrategy<EmptyPayload> {

	private final UnpublishProductPostUseCase unpublishProductPostUseCase;

	@Override
	public ScheduleActionType getType() {
		return ScheduleActionType.UNPUBLISH;
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
		unpublishProductPostUseCase.execute(
			UnpublishProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}