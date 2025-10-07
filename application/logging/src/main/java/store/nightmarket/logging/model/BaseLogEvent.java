package store.nightmarket.logging.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseLogEvent {
	private String eventType;   // HTTP / FEIGN / KAFKA
	private String direction;   // IN / OUT / PRODUCE / CONSUME
}
