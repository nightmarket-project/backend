package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.post.UpdateProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.UpdateProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.payload.UpdatePostPayload;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

@Component
@RequiredArgsConstructor
public class UpdateSchedulePostStrategy implements SchedulePostActionStrategy {

	private final UpdateProductPostUseCase updateProductPostUseCase;
	private final ObjectMapper objectMapper;

	@Override
	public SchedulePostActionType getType() {
		return SchedulePostActionType.UPDATE;
	}

	@Override
	public void execute(SchedulePost schedulePost) {
		if (schedulePost.getContext() == null) {
			throw new ProductPostException("UPDATE requires payload");
		}

		UpdatePostPayload payload = deserialize(schedulePost.getContext());

		updateProductPostUseCase.execute(
			UpdateProductPostUseCaseDto.Input.builder()
				.productPostId(schedulePost.getProductPostId())
				.rating(payload.rating())
				.build()
		);
	}

	private UpdatePostPayload deserialize(String context) {
		try {
			return objectMapper.readValue(context, UpdatePostPayload.class);
		} catch (Exception e) {
			throw new ProductPostException("Invalid UPDATE payload");
		}
	}

}