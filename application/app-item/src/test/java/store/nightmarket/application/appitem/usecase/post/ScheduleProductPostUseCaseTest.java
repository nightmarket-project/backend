package store.nightmarket.application.appitem.usecase.post;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.ScheduleProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

public class ScheduleProductPostUseCaseTest {

	private ScheduleProductPostUseCase scheduleProductPostUseCase;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private SchedulingService mockSchedulingService;
	private ExecuteSchedulePostUseCase mockExecuteSchedulePostUseCase;

	@BeforeEach
	void setUp() {
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockSchedulingService = mock(SchedulingService.class);
		mockExecuteSchedulePostUseCase = mock(ExecuteSchedulePostUseCase.class);
		scheduleProductPostUseCase = new ScheduleProductPostUseCase(
			mockSaveSchedulePostPort,
			mockSchedulingService,
			mockExecuteSchedulePostUseCase
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
			.type(ScheduleActionType.PUBLISH)
			.build();

		// when
		scheduleProductPostUseCase.execute(input);

		// then
		verify(mockSaveSchedulePostPort, times(1)).save(any(SchedulePost.class));

		verify(mockSchedulingService, times(1)).addSchedule(
			any(ExecuteSchedulePostUseCaseDto.Input.class),
			any(),
			any(LocalDateTime.class),
			any(SchedulePostId.class)
		);

	}

}
