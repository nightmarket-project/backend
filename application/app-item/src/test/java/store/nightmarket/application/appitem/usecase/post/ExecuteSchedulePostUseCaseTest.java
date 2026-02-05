package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.strategy.ScheduleActionStrategy;
import store.nightmarket.application.appitem.usecase.post.strategy.ScheduleActionStrategyRegistry;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.EmptyPayload;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.UpdatePostPayload;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@ExtendWith(MockitoExtension.class)
public class ExecuteSchedulePostUseCaseTest {

	@Mock
	ReadSchedulePostPort readSchedulePostPort;

	@Mock
	SaveSchedulePostPort saveSchedulePostPort;

	@Mock
	ScheduleActionStrategyRegistry scheduleActionStrategyRegistry;

	ObjectMapper objectMapper = new ObjectMapper();

	ExecuteSchedulePostUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new ExecuteSchedulePostUseCase(
			readSchedulePostPort,
			saveSchedulePostPort,
			scheduleActionStrategyRegistry,
			objectMapper
		);
	}

	@Test
	@DisplayName("payload_없는_전략은_execute만_호출된다")
	void whenPayloadIsEmptyThenOnlyCallExecute() {
		// given
		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = TestDomainFactory.createSchedulePost(
			schedulePostId.getId(),
			productPostId.getId(),
			ScheduleActionType.PUBLISH,
			null
		);

		ScheduleActionStrategy strategy = mock(ScheduleActionStrategy.class);

		when(readSchedulePostPort.readOrThrow(schedulePost.getSchedulePostId()))
			.thenReturn(schedulePost);

		when(scheduleActionStrategyRegistry.get(ScheduleActionType.PUBLISH))
			.thenReturn(strategy);

		when(strategy.requiresPayload()).thenReturn(false);

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePost.getSchedulePostId())
			.build();

		// when
		useCase.execute(input);

		// then
		verify(strategy).execute(any(SchedulePost.class), any(EmptyPayload.class));
		verify(saveSchedulePostPort).save(schedulePost);
		assertThat(schedulePost.isDone()).isTrue();
	}

	@Test
	@DisplayName("payload_있는_전략은_JSON을_역직렬화해서_전달한다")
	void whenPayloadIsNotEmptyThenDeserialize() {
		// given
		String contextJson = """
				{ "rating": { "value": 4.5 } }
			""";

		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = TestDomainFactory.createSchedulePost(
			schedulePostId.getId(),
			productPostId.getId(),
			ScheduleActionType.UPDATE,
			contextJson
		);

		ScheduleActionStrategy strategy = mock(ScheduleActionStrategy.class);

		when(readSchedulePostPort.readOrThrow(schedulePost.getSchedulePostId()))
			.thenReturn(schedulePost);

		when(scheduleActionStrategyRegistry.get(ScheduleActionType.UPDATE))
			.thenReturn(strategy);

		when(strategy.requiresPayload()).thenReturn(true);
		when(strategy.payloadClass()).thenReturn(UpdatePostPayload.class);

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePost.getSchedulePostId())
			.build();

		// when
		useCase.execute(input);

		// then
		ArgumentCaptor<UpdatePostPayload> captor = ArgumentCaptor.forClass(UpdatePostPayload.class);

		verify(strategy).execute(eq(schedulePost), captor.capture());

		assertThat(captor.getValue().rating().value()).isEqualTo(4.5f);
		verify(saveSchedulePostPort).save(schedulePost);
	}

}
