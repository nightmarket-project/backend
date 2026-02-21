package store.nightmarket.application.appitem.usecase.post.schedule;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.event.SchedulePostCreatedEvent;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.ScheduleProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.schedule.usecase.dto.ScheduleProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

public class ScheduleProductPostUseCaseTest {

	private ScheduleProductPostUseCase scheduleProductPostUseCase;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private ObjectMapper objectMapper;
	private ApplicationEventPublisher mockApplicationEventPublisher;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockApplicationEventPublisher = mock(ApplicationEventPublisher.class);
		scheduleProductPostUseCase = new ScheduleProductPostUseCase(
			mockSaveSchedulePostPort,
			objectMapper,
			mockApplicationEventPublisher
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

		verify(mockApplicationEventPublisher, times(1)).publishEvent(any(SchedulePostCreatedEvent.class));

	}

}
