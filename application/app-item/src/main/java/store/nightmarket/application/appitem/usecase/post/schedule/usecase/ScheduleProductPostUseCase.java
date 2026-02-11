package store.nightmarket.application.appitem.usecase.post.schedule.usecase;

import static store.nightmarket.application.appitem.usecase.post.schedule.usecase.dto.ScheduleProductPostUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@Service
@RequiredArgsConstructor
public class ScheduleProductPostUseCase implements BaseUseCase<Input, Void> {

	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulingService schedulingService;
	private final SchedulePostStrategyExecutor schedulePostStrategyExecutor;
	private final ObjectMapper objectMapper;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = createSchedulePost(input);

		saveSchedulePostPort.save(schedulePost);

		SchedulePostStrategyExecutorDto.Input executorInput = SchedulePostStrategyExecutorDto.Input.builder()
			.schedulePostId(schedulePost.getSchedulePostId())
			.build();

		schedulingService.addSchedule(
			executorInput,
			schedulePostStrategyExecutor::execute,
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
			input.type(),
			serializeContext(input)
		);
	}

	private String serializeContext(Input input) {
		String context = null;

		if (input.payload() != null) {
			try {
				context = objectMapper.writeValueAsString(input.payload());
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
		return context;
	}

}
