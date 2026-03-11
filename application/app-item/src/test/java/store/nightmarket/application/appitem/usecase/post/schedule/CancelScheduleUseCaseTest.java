package store.nightmarket.application.appitem.usecase.post.schedule;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.event.SchedulePostCanceledEvent;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.CancelScheduleUseCase;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.dto.CancelScheduleUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

public class CancelScheduleUseCaseTest {

	private CancelScheduleUseCase cancelScheduleUseCase;
	private ReadSchedulePostPort mockReadSchedulePostPort;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private ApplicationEventPublisher mockEventPublisher;

	@BeforeEach
	void setUp() {
		mockReadSchedulePostPort = mock(ReadSchedulePostPort.class);
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockEventPublisher = mock(ApplicationEventPublisher.class);
		cancelScheduleUseCase = new CancelScheduleUseCase(
			mockReadSchedulePostPort,
			mockSaveSchedulePostPort,
			mockEventPublisher
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
			SchedulePostState.READY,
			SchedulePostActionType.PUBLISH,
			null
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

		verify(mockEventPublisher, times(1))
			.publishEvent(any(SchedulePostCanceledEvent.class));

	}

}
