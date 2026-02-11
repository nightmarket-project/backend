package store.nightmarket.application.appitem.usecase.schedule;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.schedule.RescheduleService;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

public class RescheduleServiceTest {

	private RescheduleService rescheduleService;
	private SchedulingService mockSchedulingService;
	private ReadSchedulePostPort mockReadSchedulePostPort;
	private SchedulePostStrategyExecutor mockSchedulePostStrategyExecutor;

	@BeforeEach
	void setUp() {
		mockSchedulingService = mock(SchedulingService.class);
		mockReadSchedulePostPort = mock(ReadSchedulePostPort.class);
		mockSchedulePostStrategyExecutor = mock(SchedulePostStrategyExecutor.class);
		rescheduleService = new RescheduleService(
			mockSchedulingService,
			mockReadSchedulePostPort,
			mockSchedulePostStrategyExecutor
		);
	}

	@Test
	@DisplayName("READY_상태의_스케줄을_모두_다시_등록한다")
	void restoreScheduleWhenStateIsReady() {
		// given
		SchedulePost schedule1 = SchedulePost.newInstance(
			new SchedulePostId(UUID.randomUUID()),
			new ProductPostId(UUID.randomUUID()),
			LocalDateTime.now().plusMinutes(10),
			SchedulePostState.READY,
			SchedulePostActionType.PUBLISH,
			null
		);

		SchedulePost schedule2 = SchedulePost.newInstance(
			new SchedulePostId(UUID.randomUUID()),
			new ProductPostId(UUID.randomUUID()),
			LocalDateTime.now().plusMinutes(20),
			SchedulePostState.READY,
			SchedulePostActionType.UNPUBLISH,
			null
		);

		when(mockReadSchedulePostPort.readAllByReady())
			.thenReturn(List.of(schedule1, schedule2));

		ArgumentCaptor<SchedulePostStrategyExecutorDto.Input> inputCaptor = ArgumentCaptor.forClass(
			SchedulePostStrategyExecutorDto.Input.class);

		// when
		rescheduleService.restoreSchedulePosts();

		// then
		verify(mockReadSchedulePostPort, times(1)).readAllByReady();

		verify(mockSchedulingService, times(2)).addSchedule(
			inputCaptor.capture(),
			any(),
			any(LocalDateTime.class),
			any(SchedulePostId.class)
		);

		List<SchedulePostStrategyExecutorDto.Input> capturedInputs = inputCaptor.getAllValues();

		Assertions.assertThat(capturedInputs)
			.map(SchedulePostStrategyExecutorDto.Input::schedulePostId)
			.containsExactlyInAnyOrder(
				schedule1.getSchedulePostId(),
				schedule2.getSchedulePostId()
			);
	}

}
