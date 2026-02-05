package store.nightmarket.application.appitem.usecase.post.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.DeleteProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.EmptyPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@Component
@RequiredArgsConstructor
public class DeleteScheduleStrategy implements ScheduleActionStrategy<EmptyPayload> {

	private final DeleteProductPostUseCase deleteProductPostUseCase;

	@Override
	public ScheduleActionType getType() {
		return ScheduleActionType.DELETE;
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
		deleteProductPostUseCase.execute(
			DeleteProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}