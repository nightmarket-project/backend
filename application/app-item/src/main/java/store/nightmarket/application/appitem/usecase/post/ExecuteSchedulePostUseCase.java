package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Service
@RequiredArgsConstructor
public class ExecuteSchedulePostUseCase implements BaseUseCase<Input, Void> {

	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final PublishProductPostUseCase publishProductPostUseCase;
	private final UnpublishProductPostUseCase unpublishProductPostUseCase;
	private final DeleteProductPostUseCase deleteProductPostUseCase;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = readSchedulePostPort.readOrThrow(input.schedulePostId());

		if (schedulePost.isDone()) {
			return null;
		}

		switch (schedulePost.getType()) {
			case PUBLISH -> publishProductPostUseCase.execute(
				PublishProductPostUseCaseDto.Input.builder()
					.productPostId(schedulePost.getProductPostId())
					.build()
			);
			case UNPUBLISH -> unpublishProductPostUseCase.execute(
				UnpublishProductPostUseCaseDto.Input.builder()
					.productPostId(schedulePost.getProductPostId())
					.build()
			);
			case DELETE -> deleteProductPostUseCase.execute(
				DeleteProductPostUseCaseDto.Input.builder()
					.productPostId(schedulePost.getProductPostId())
					.build()
			);
		}

		schedulePost.done();
		saveSchedulePostPort.save(schedulePost);
		return null;
	}
}