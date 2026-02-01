package store.nightmarket.application.appitem.usecase.post;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.SchedulingService;
import store.nightmarket.application.appitem.usecase.post.dto.CancelScheduleUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

public class CancelScheduleUseCaseTest {

	private CancelScheduleUseCase cancelScheduleUseCase;
	private ReadSchedulePostPort mockReadSchedulePostPort;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private SchedulingService mockSchedulingService;

	@BeforeEach
	void setUp() {
		mockReadSchedulePostPort = mock(ReadSchedulePostPort.class);
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockSchedulingService = mock(SchedulingService.class);
		cancelScheduleUseCase = new CancelScheduleUseCase(
			mockReadSchedulePostPort,
			mockSaveSchedulePostPort,
			mockSchedulingService
		);
	}

	@Test
	@DisplayName("예약을 취소한다")
	void cancelSchedule() {
		// given
		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = TestDomainFactory.createSchedulePost(
			schedulePostId.getId(),
			productPostId.getId(),
			ScheduleActionType.PUBLISH
		);

		when(mockReadSchedulePostPort.readOrThrow(schedulePostId))
			.thenReturn(schedulePost);

		CancelScheduleUseCaseDto.Input input = CancelScheduleUseCaseDto.Input.builder()
			.schedulePostId(schedulePostId)
			.build();

		// when
		cancelScheduleUseCase.execute(input);

		// then
		ArgumentCaptor<SchedulePost> argumentCaptor = ArgumentCaptor.forClass(SchedulePost.class);

		verify(mockSaveSchedulePostPort, times(1))
			.save(argumentCaptor.capture());

		SchedulePost savedSchedulePost = argumentCaptor.getValue();

		Assertions.assertThat(savedSchedulePost.getState()).isEqualTo(SchedulePostState.CANCELED);

		verify(mockSchedulingService, times(1))
			.cancelSchedule(any(SchedulePostId.class));

	}

}
