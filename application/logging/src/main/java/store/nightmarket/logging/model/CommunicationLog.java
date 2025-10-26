package store.nightmarket.logging.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class CommunicationLog {
	private TypeEnum eventType;
}
