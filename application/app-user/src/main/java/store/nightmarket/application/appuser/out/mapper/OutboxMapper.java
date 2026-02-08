package store.nightmarket.application.appuser.out.mapper;

import store.nightmarket.domain.user.model.Outbox;
import store.nightmarket.domain.user.model.id.OutboxId;
import store.nightmarket.persistence.persistuser.entity.model.OutboxEntity;

public class OutboxMapper {

	public static Outbox toDomain(OutboxEntity entity) {
		return Outbox.newInstanceWithId(
			new OutboxId(entity.getId()),
			entity.getAggregateType(),
			entity.getAggregateId(),
			entity.getEventType(),
			entity.getPayload(),
			entity.getState()
		);
	}

	public static OutboxEntity toEntity(Outbox domain) {
		return OutboxEntity.newInstance(
			domain.getAggregateType(),
			domain.getAggregateId(),
			domain.getEventType(),
			domain.getPayload(),
			domain.getState()
		);
	}

}
