package store.nightmarket.logging;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class CommunicationLog {

	private CommunicationLog.Type eventType;

    public enum Type {
        ACCESS,
        KAFKA,
        FEIGN;
    }

}
