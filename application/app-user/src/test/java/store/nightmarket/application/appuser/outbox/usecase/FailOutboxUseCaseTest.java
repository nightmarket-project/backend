package store.nightmarket.application.appuser.outbox.usecase;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appuser.out.ReadOutboxPort;
import store.nightmarket.application.appuser.out.SaveOutboxPort;
import store.nightmarket.application.appuser.outbox.fixture.TestFixture;
import store.nightmarket.application.appuser.outbox.usecase.dto.FailOutboxUseCaseDto;
import store.nightmarket.domain.user.model.Outbox;
import store.nightmarket.domain.user.model.OutboxState;
import store.nightmarket.domain.user.model.id.OutboxId;

public class FailOutboxUseCaseTest {

	private FailOutboxUseCase failOutboxUseCase;
	private ReadOutboxPort mockReadOutboxPort;
	private SaveOutboxPort mockSaveOutboxPort;

	@BeforeEach
	void setUp() {
		mockReadOutboxPort = mock(ReadOutboxPort.class);
		mockSaveOutboxPort = mock(SaveOutboxPort.class);
		failOutboxUseCase = new FailOutboxUseCase(
			mockReadOutboxPort,
			mockSaveOutboxPort
		);
	}

	@Test
	@DisplayName("상태를 Failed로 변경한다")
	void ChangeStateFailed() {
		// given
		OutboxId outboxId = new OutboxId(1L);

		Outbox outbox = TestFixture.createOutbox(outboxId);

		when(mockReadOutboxPort.readOrThrow(outboxId.getId()))
			.thenReturn(outbox);

		// when
		failOutboxUseCase.execute(
			FailOutboxUseCaseDto.Input.builder()
				.outboxId(outboxId)
				.build()
		);

		// then
		ArgumentCaptor<Outbox> argumentCaptor = ArgumentCaptor.forClass(Outbox.class);

		verify(mockSaveOutboxPort, times(1))
			.save(argumentCaptor.capture());

		Outbox savedOutbox = argumentCaptor.getValue();

		Assertions.assertThat(savedOutbox.getState()).isEqualTo(OutboxState.FAILED);
	}

}
