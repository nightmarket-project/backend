package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ScheduleProductPostUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@Service
@RequiredArgsConstructor
public class ScheduleProductPostUseCase implements BaseUseCase<Input, Void> {

	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulingService schedulingService;
	private final ExecuteSchedulePostUseCase executeSchedulePostUseCase;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = createSchedulePost(input);

		saveSchedulePostPort.save(schedulePost);

		ExecuteSchedulePostUseCaseDto.Input executeInput = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePost.getSchedulePostId())
			.build();

		schedulingService.addSchedule(
			executeInput,
			executeSchedulePostUseCase::execute,
			input.scheduledAt(),
			schedulePost.getSchedulePostId()
		);
		return null;
	}

	private SchedulePost createSchedulePost(Input input) {
		return SchedulePost.newInstance(
			new SchedulePostId(UUID.randomUUID()),
			input.productPostId(),
			input.scheduledAt(),
			SchedulePostState.READY,
			input.type()
		);
	}

}
