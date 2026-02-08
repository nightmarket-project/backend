package store.nightmarket.persistence.persistuser.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.domain.user.model.OutboxState;

@Getter
@Entity
@Table(name = "outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEntity extends BaseAutoIncrementIdEntity {

	@Column(name = "aggregate_type", nullable = false)
	private String aggregateType;

	@Column(name = "aggregate_id", nullable = false)
	private String aggregateId;

	@Column(name = "event_type", nullable = false)
	private String eventType;

	@Column(name = "payload")
	private String payload;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private OutboxState state;

	private OutboxEntity(
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
	}

	public static OutboxEntity newInstance(
		String aggregateType,
		String aggregateId,
		String eventType,
		String payload,
		OutboxState state
	) {
		return new OutboxEntity(
			aggregateType,
			aggregateId,
			eventType,
			payload,
			state
		);
	}

}
