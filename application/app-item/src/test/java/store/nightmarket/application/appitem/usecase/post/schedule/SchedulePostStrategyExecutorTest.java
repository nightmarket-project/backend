package store.nightmarket.application.appitem.usecase.post.schedule;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.SchedulePostActionStrategy;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.SchedulePostActionStrategyRegistry;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

@ExtendWith(MockitoExtension.class)
public class SchedulePostStrategyExecutorTest {

	@Mock
	ReadSchedulePostPort readSchedulePostPort;

	@Mock
	SaveSchedulePostPort saveSchedulePostPort;

	@Mock
	SchedulePostActionStrategyRegistry schedulePostActionStrategyRegistry;

	SchedulePostStrategyExecutor useCase;

	@BeforeEach
	void setUp() {
		useCase = new SchedulePostStrategyExecutor(
			readSchedulePostPort,
			saveSchedulePostPort,
			schedulePostActionStrategyRegistry
		);
	}

	@Test
	@DisplayName("맞는 전략을 실행한 후 예약상태를 완료로 변경한다")
	void whenExecuteCorrectStrategyThenChangeStateDone() {
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

		SchedulePostActionStrategy strategy = mock(SchedulePostActionStrategy.class);

		when(readSchedulePostPort.readOrThrow(schedulePost.getSchedulePostId()))
			.thenReturn(schedulePost);

		when(schedulePostActionStrategyRegistry.get(SchedulePostActionType.PUBLISH))
			.thenReturn(strategy);

		SchedulePostStrategyExecutorDto.Input input = SchedulePostStrategyExecutorDto.Input.builder()
			.schedulePostId(schedulePost.getSchedulePostId())
			.build();

		// when
		useCase.execute(input);

		// then
		verify(strategy).execute(any(SchedulePost.class));
		verify(saveSchedulePostPort).save(schedulePost);
		assertThat(schedulePost.isDone()).isTrue();
	}

}
