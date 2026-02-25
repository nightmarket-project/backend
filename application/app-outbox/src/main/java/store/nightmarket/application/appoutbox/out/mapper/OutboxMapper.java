package store.nightmarket.application.appoutbox.out.mapper;

import store.nightmarket.domain.outbox.model.Outbox;
import store.nightmarket.domain.outbox.model.id.OutboxId;
import store.nightmarket.persistence.persistoutbox.model.OutboxEntity;

public class OutboxMapper {

	public static Outbox toDomain(OutboxEntity entity) {
		return Outbox.newInstanceWithId(
			new OutboxId(entity.getId()),
			entity.getEventType(),
			entity.getPayload(),
			entity.getState()
		);
	}

	public static OutboxEntity toEntity(Outbox domain) {
		return OutboxEntity.newInstance(
			domain.getEventType(),
			domain.getPayload(),
			domain.getState()
		);
	}

	public static OutboxEntity toEntityWithId(Outbox domain) {
		return OutboxEntity.newInstanceWithId(
			domain.getOutboxId().getId(),
			domain.getEventType(),
			domain.getPayload(),
			domain.getState()
		);
	}

}
