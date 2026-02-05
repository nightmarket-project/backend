package store.nightmarket.application.appitem.usecase.post.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.UpdateProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.UpdateProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.UpdatePostPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@Component
@RequiredArgsConstructor
public class UpdateScheduleStrategy implements ScheduleActionStrategy<UpdatePostPayload> {

	private final UpdateProductPostUseCase updateProductPostUseCase;

	@Override
	public ScheduleActionType getType() {
		return ScheduleActionType.UPDATE;
	}

	@Override
	public Class<UpdatePostPayload> payloadClass() {
		return UpdatePostPayload.class;
	}

	@Override
	public boolean requiresPayload() {
		return true;
	}

	@Override
	public void execute(SchedulePost schedulePost, UpdatePostPayload payload) {
		updateProductPostUseCase.execute(
			UpdateProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.rating(payload.rating())
				.build()
		);
	}

}