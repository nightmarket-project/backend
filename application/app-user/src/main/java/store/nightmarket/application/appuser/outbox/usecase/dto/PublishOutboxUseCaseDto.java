package store.nightmarket.application.appuser.outbox.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.user.model.id.OutboxId;

public class PublishOutboxUseCaseDto {

	@Builder
	public record Input(
		OutboxId outboxId
	) {

	}

}
