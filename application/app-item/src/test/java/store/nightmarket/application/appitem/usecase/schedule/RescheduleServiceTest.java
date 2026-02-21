package store.nightmarket.application.appitem.usecase.schedule;

import static org.mockito.Mockito.*;
import static store.nightmarket.application.appitem.fixture.TestDomainFactory.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.config.SchedulingProperties;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.RescheduleService;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

public class RescheduleServiceTest {

	private RescheduleService rescheduleService;
	private SchedulingProperties mockSchedulingProperties;
	private ReadSchedulePostPort mockReadSchedulePostPort;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private SchedulingService mockSchedulingService;
	private SchedulePostStrategyExecutor mockSchedulePostStrategyExecutor;

	private static final int BATCH_PERIOD = 24;
	private static final int CHUNK_SIZE = 100;

	@BeforeEach
	void setUp() {
		mockSchedulingProperties = mock(SchedulingProperties.class);
		mockSchedulingService = mock(SchedulingService.class);
		mockReadSchedulePostPort = mock(ReadSchedulePostPort.class);
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockSchedulePostStrategyExecutor = mock(SchedulePostStrategyExecutor.class);
		rescheduleService = new RescheduleService(
			mockSchedulingProperties,
			mockReadSchedulePostPort,
			mockSaveSchedulePostPort,
			mockSchedulingService,
			mockSchedulePostStrategyExecutor
		);

		when(mockSchedulingProperties.getBatchPeriod()).thenReturn(BATCH_PERIOD);
		when(mockSchedulingProperties.getBatchChunkSize()).thenReturn(CHUNK_SIZE);
	}

	@Test
	@DisplayName("READY와 SCHEDULED 상태의 스케줄을 모두 다시 등록한다")
	void restoreScheduleWhenStateIsReadyAndScheduled() {
		// given
		SchedulePost readySchedule1 = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.READY,
			SchedulePostActionType.PUBLISH,
			null
		);
		SchedulePost readySchedule2 = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.READY,
			SchedulePostActionType.UNPUBLISH,
			null
		);
		SchedulePost scheduledSchedule1 = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.SCHEDULED,
			SchedulePostActionType.PUBLISH,
			null
		);
		SchedulePost scheduledSchedule2 = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.SCHEDULED,
			SchedulePostActionType.UNPUBLISH,
			null
		);

		when(mockReadSchedulePostPort.readReadyChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(List.of(readySchedule1, readySchedule2));
		when(mockReadSchedulePostPort.readScheduledChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(List.of(scheduledSchedule1, scheduledSchedule2));

		// when
		rescheduleService.restoreSchedulePosts();

		// then
		verify(mockReadSchedulePostPort, times(1)).readReadyChunk(any(LocalDateTime.class), eq(CHUNK_SIZE));
		verify(mockReadSchedulePostPort, times(1)).readScheduledChunk(any(LocalDateTime.class), eq(CHUNK_SIZE));

		ArgumentCaptor<SchedulePostStrategyExecutorDto.Input> inputCaptor =
			ArgumentCaptor.forClass(SchedulePostStrategyExecutorDto.Input.class);

		verify(mockSchedulingService, times(4)).addSchedule(
			inputCaptor.capture(),
			any(),
			any(LocalDateTime.class),
			any(SchedulePostId.class)
		);

		Assertions.assertThat(inputCaptor.getAllValues())
			.map(SchedulePostStrategyExecutorDto.Input::schedulePostId)
			.containsExactlyInAnyOrder(
				readySchedule1.getSchedulePostId(),
				readySchedule2.getSchedulePostId(),
				scheduledSchedule1.getSchedulePostId(),
				scheduledSchedule2.getSchedulePostId()
			);
	}

	@Test
	@DisplayName("복구 대상이 없으면 메모리 등록을 하지 않는다")
	void doNothingWhenNoScheduleToRestore() {
		// given
		when(mockReadSchedulePostPort.readReadyChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(Collections.emptyList());

		when(mockReadSchedulePostPort.readScheduledChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(Collections.emptyList());

		// when
		rescheduleService.restoreSchedulePosts();

		// then
		verify(mockSchedulingService, never()).addSchedule(any(), any(), any(), any());
		verify(mockSaveSchedulePostPort, never()).save(any());
	}

	@Test
	@DisplayName("READY 상태는 복구 후 SCHEDULED로 상태가 전이된다")
	void readyScheduleTransitionToScheduledAfterRestore() {
		// given
		SchedulePost readySchedule = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.READY,
			SchedulePostActionType.PUBLISH,
			null
		);

		when(mockReadSchedulePostPort.readReadyChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(List.of(readySchedule));
		when(mockReadSchedulePostPort.readScheduledChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(Collections.emptyList());

		// when
		rescheduleService.restoreSchedulePosts();

		// then
		Assertions.assertThat(readySchedule.getState()).isEqualTo(SchedulePostState.SCHEDULED);
		verify(mockSaveSchedulePostPort, times(1)).save(readySchedule);
	}

	@Test
	@DisplayName("SCHEDULED 상태는 상태 전이 없이 메모리에만 재등록된다")
	void scheduledStateOnlyReregisteredToMemoryWithoutStateTransition() {
		// given
		SchedulePost scheduledSchedule = createSchedulePost(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SchedulePostState.SCHEDULED,
			SchedulePostActionType.PUBLISH,
			null
		);

		when(mockReadSchedulePostPort.readReadyChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(Collections.emptyList());
		when(mockReadSchedulePostPort.readScheduledChunk(any(LocalDateTime.class), eq(CHUNK_SIZE)))
			.thenReturn(List.of(scheduledSchedule));

		// when
		rescheduleService.restoreSchedulePosts();

		// then
		Assertions.assertThat(scheduledSchedule.getState()).isEqualTo(SchedulePostState.SCHEDULED);
		verify(mockSaveSchedulePostPort, never()).save(any());
		verify(mockSchedulingService, times(1)).addSchedule(any(), any(), any(), any());
	}

}
