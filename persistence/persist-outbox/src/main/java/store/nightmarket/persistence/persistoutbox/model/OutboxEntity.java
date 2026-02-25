package store.nightmarket.persistence.persistoutbox.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.domain.outbox.model.OutboxState;

@Getter
@Entity
@Table(name = "outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEntity extends BaseAutoIncrementIdEntity {

	@Column(name = "event_type", nullable = false)
	private String eventType;

	@Column(name = "payload")
	private String payload;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private OutboxState state;

	private OutboxEntity(
		String eventType,
		String payload,
		OutboxState state
	) {
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
	}

	private OutboxEntity(
		Long id,
		String eventType,
		String payload,
		OutboxState state
	) {
		super(id);
		this.eventType = eventType;
		this.payload = payload;
		this.state = state;
	}

	public static OutboxEntity newInstance(
		String eventType,
		String payload,
		OutboxState state
	) {
		return new OutboxEntity(
			eventType,
			payload,
			state
		);
	}

	public static OutboxEntity newInstanceWithId(
		Long id,
		String eventType,
		String payload,
		OutboxState state
	) {
		return new OutboxEntity(
			id,
			eventType,
			payload,
			state
		);
	}

}
