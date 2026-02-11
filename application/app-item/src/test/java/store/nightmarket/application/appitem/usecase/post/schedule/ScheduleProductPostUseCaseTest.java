package store.nightmarket.application.appitem.usecase.post.schedule;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.ScheduleProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.dto.ScheduleProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

public class ScheduleProductPostUseCaseTest {

	private ScheduleProductPostUseCase scheduleProductPostUseCase;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private SchedulingService mockSchedulingService;
	private SchedulePostStrategyExecutor mockSchedulePostStrategyExecutor;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockSchedulingService = mock(SchedulingService.class);
		mockSchedulePostStrategyExecutor = mock(SchedulePostStrategyExecutor.class);
		scheduleProductPostUseCase = new ScheduleProductPostUseCase(
			mockSaveSchedulePostPort,
			mockSchedulingService,
			mockSchedulePostStrategyExecutor,
			objectMapper
		);
	}

	@Test
	@DisplayName("상품 게시글을 예약한다")
	void schedulePost() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		LocalDateTime scheduledAt = LocalDateTime.now().plusHours(1);

		ScheduleProductPostUseCaseDto.Input input = ScheduleProductPostUseCaseDto.Input.builder()
			.productPostId(productPostId)
			.scheduledAt(scheduledAt)
			.type(SchedulePostActionType.PUBLISH)
			.build();

		// when
		scheduleProductPostUseCase.execute(input);

		// then
		verify(mockSaveSchedulePostPort, times(1)).save(any(SchedulePost.class));

		verify(mockSchedulingService, times(1)).addSchedule(
			any(SchedulePostStrategyExecutorDto.Input.class),
			any(),
			any(LocalDateTime.class),
			any(SchedulePostId.class)
		);

	}

}
