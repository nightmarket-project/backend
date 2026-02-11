package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.DeleteProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

@Component
@RequiredArgsConstructor
public class DeleteSchedulePostStrategy implements SchedulePostActionStrategy {

	private final DeleteProductPostUseCase deleteProductPostUseCase;

	@Override
	public SchedulePostActionType getType() {
		return SchedulePostActionType.DELETE;
	}

	@Override
	public void execute(SchedulePost schedulePost) {
		deleteProductPostUseCase.execute(
			DeleteProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.build()
		);
	}

}