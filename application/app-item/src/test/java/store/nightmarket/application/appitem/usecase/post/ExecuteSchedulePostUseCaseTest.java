package store.nightmarket.application.appitem.usecase.post;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;

public class ExecuteSchedulePostUseCaseTest {

	private ExecuteSchedulePostUseCase executeSchedulePostUseCase;
	private ReadSchedulePostPort mockReadSchedulePostPort;
	private SaveSchedulePostPort mockSaveSchedulePostPort;
	private PublishProductPostUseCase mockPublishProductPostUseCase;
	private UnpublishProductPostUseCase mockUnpublishProductPostUseCase;
	private DeleteProductPostUseCase mockDeleteProductPostUseCase;

	@BeforeEach
	void setUp() {
		mockReadSchedulePostPort = mock(ReadSchedulePostPort.class);
		mockSaveSchedulePostPort = mock(SaveSchedulePostPort.class);
		mockPublishProductPostUseCase = mock(PublishProductPostUseCase.class);
		mockUnpublishProductPostUseCase = mock(UnpublishProductPostUseCase.class);
		mockDeleteProductPostUseCase = mock(DeleteProductPostUseCase.class);
		executeSchedulePostUseCase = new ExecuteSchedulePostUseCase(
			mockReadSchedulePostPort,
			mockSaveSchedulePostPort,
			mockPublishProductPostUseCase,
			mockUnpublishProductPostUseCase,
			mockDeleteProductPostUseCase
		);
	}

	@Test
	@DisplayName("상품 게시글을 게시하고 게시예약을 완료로 변경한다")
	void publishPostAndScheduleDone() {
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

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePostId)
			.build();

		// when
		executeSchedulePostUseCase.execute(input);

		// then
		verify(mockReadSchedulePostPort, times(1))
			.readOrThrow(schedulePostId);

		verify(mockPublishProductPostUseCase, times(1))
			.execute(any(PublishProductPostUseCaseDto.Input.class));

		verify(mockUnpublishProductPostUseCase, never())
			.execute(any(UnpublishProductPostUseCaseDto.Input.class));

		verify(mockDeleteProductPostUseCase, never())
			.execute(any(DeleteProductPostUseCaseDto.Input.class));

		ArgumentCaptor<SchedulePost> argumentCaptor = ArgumentCaptor.forClass(SchedulePost.class);

		verify(mockSaveSchedulePostPort, times(1))
			.save(argumentCaptor.capture());

		SchedulePost savedSchedulePost = argumentCaptor.getValue();

		Assertions.assertThat(savedSchedulePost.getState()).isEqualTo(SchedulePostState.DONE);

	}

	@Test
	@DisplayName("상품 게시글을 내리고 게시예약을 완료로 변경한다")
	void unpublishPostAndScheduleDone() {
		// given
		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = TestDomainFactory.createSchedulePost(
			schedulePostId.getId(),
			productPostId.getId(),
			ScheduleActionType.UNPUBLISH
		);

		when(mockReadSchedulePostPort.readOrThrow(schedulePostId))
			.thenReturn(schedulePost);

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePostId)
			.build();

		// when
		executeSchedulePostUseCase.execute(input);

		// then
		verify(mockReadSchedulePostPort, times(1))
			.readOrThrow(schedulePostId);

		verify(mockPublishProductPostUseCase, never())
			.execute(any(PublishProductPostUseCaseDto.Input.class));

		verify(mockUnpublishProductPostUseCase, times(1))
			.execute(any(UnpublishProductPostUseCaseDto.Input.class));

		verify(mockDeleteProductPostUseCase, never())
			.execute(any(DeleteProductPostUseCaseDto.Input.class));

		ArgumentCaptor<SchedulePost> argumentCaptor = ArgumentCaptor.forClass(SchedulePost.class);

		verify(mockSaveSchedulePostPort, times(1))
			.save(argumentCaptor.capture());

		SchedulePost savedSchedulePost = argumentCaptor.getValue();

		Assertions.assertThat(savedSchedulePost.getState()).isEqualTo(SchedulePostState.DONE);

	}

	@Test
	@DisplayName("상품 게시글을 삭제하고 게시예약을 완료로 변경한다")
	void deletePostAndScheduleDone() {
		// given
		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = TestDomainFactory.createSchedulePost(
			schedulePostId.getId(),
			productPostId.getId(),
			ScheduleActionType.DELETE
		);

		when(mockReadSchedulePostPort.readOrThrow(schedulePostId))
			.thenReturn(schedulePost);

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePostId)
			.build();

		// when
		executeSchedulePostUseCase.execute(input);

		// then
		verify(mockReadSchedulePostPort, times(1))
			.readOrThrow(schedulePostId);

		verify(mockPublishProductPostUseCase, never())
			.execute(any(PublishProductPostUseCaseDto.Input.class));

		verify(mockUnpublishProductPostUseCase, never())
			.execute(any(UnpublishProductPostUseCaseDto.Input.class));

		verify(mockDeleteProductPostUseCase, times(1))
			.execute(any(DeleteProductPostUseCaseDto.Input.class));

		ArgumentCaptor<SchedulePost> argumentCaptor = ArgumentCaptor.forClass(SchedulePost.class);

		verify(mockSaveSchedulePostPort, times(1))
			.save(argumentCaptor.capture());

		SchedulePost savedSchedulePost = argumentCaptor.getValue();

		Assertions.assertThat(savedSchedulePost.getState()).isEqualTo(SchedulePostState.DONE);

	}

	@Test
	@DisplayName("게시예약상태가 완료라면 즉시 리턴한다")
	void ScheduleAlreadyDoneThenReturn() {
		// given
		SchedulePostId schedulePostId = new SchedulePostId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		SchedulePost schedulePost = SchedulePost.newInstance(
			schedulePostId,
			productPostId,
			LocalDateTime.now().plusDays(1),
			SchedulePostState.DONE,
			ScheduleActionType.PUBLISH
		);

		when(mockReadSchedulePostPort.readOrThrow(schedulePostId))
			.thenReturn(schedulePost);

		ExecuteSchedulePostUseCaseDto.Input input = ExecuteSchedulePostUseCaseDto.Input.builder()
			.schedulePostId(schedulePostId)
			.build();

		// when
		executeSchedulePostUseCase.execute(input);

		// then
		verify(mockReadSchedulePostPort, times(1))
			.readOrThrow(schedulePostId);

		verify(mockPublishProductPostUseCase, never())
			.execute(any(PublishProductPostUseCaseDto.Input.class));

		verify(mockUnpublishProductPostUseCase, never())
			.execute(any(UnpublishProductPostUseCaseDto.Input.class));

		verify(mockDeleteProductPostUseCase, never())
			.execute(any(DeleteProductPostUseCaseDto.Input.class));
		
		verify(mockSaveSchedulePostPort, never())
			.save(any(SchedulePost.class));

	}

}
