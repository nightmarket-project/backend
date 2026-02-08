package store.nightmarket.application.appuser.outbox.usecase;

import static store.nightmarket.application.appuser.outbox.usecase.dto.PublishOutboxUseCaseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.out.ReadOutboxPort;
import store.nightmarket.application.appuser.out.SaveOutboxPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.user.model.Outbox;

@Service
@RequiredArgsConstructor
public class PublishOutboxUseCase implements BaseUseCase<Input, Void> {

	private final ReadOutboxPort readOutboxPort;
	private final SaveOutboxPort saveOutboxPort;

	@Override
	@Transactional
	public Void execute(Input input) {
		Outbox outbox = readOutboxPort.readOrThrow(input.outboxId().getId());

		outbox.publish();

		saveOutboxPort.save(outbox);
		return null;
	}

}
