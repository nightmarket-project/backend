package store.nightmarket.application.appuser.outbox;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appoutbox.out.SaveOutboxPort;
import store.nightmarket.application.appuser.out.dto.UserCreatedApplicationEvent;
import store.nightmarket.application.appuser.out.dto.UserCreatedEvent;
import store.nightmarket.domain.outbox.model.Outbox;
import store.nightmarket.domain.outbox.model.OutboxState;

@Component
@RequiredArgsConstructor
public class UserCreatedOutboxEventListener {

	private final SaveOutboxPort saveOutboxPort;
	private final ObjectMapper objectMapper;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void handle(UserCreatedApplicationEvent applicationEvent) {
		UserCreatedEvent event = applicationEvent.getPayload();
		saveOutboxPort.save(
			Outbox.newInstance(
				UserOutboxEventType.USER_CREATED_EVENT.getType(),
				serializePayload(event),
				OutboxState.READY
			)
		);
	}

	private String serializePayload(UserCreatedEvent event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}