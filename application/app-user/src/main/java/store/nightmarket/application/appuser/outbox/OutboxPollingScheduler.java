package store.nightmarket.application.appuser.outbox;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.out.ReadOutboxPort;
import store.nightmarket.domain.user.model.Outbox;

@Component
@RequiredArgsConstructor
public class OutboxPollingScheduler {

	private final ReadOutboxPort readOutboxPort;
	private final KafkaEventPublisher kafkaEventPublisher;

	@Scheduled(cron = "${schedules.cron}")
	public void pollOutbox() {
		List<Outbox> outboxes = readOutboxPort.readPublishTarget();

		for (Outbox outbox : outboxes) {
			kafkaEventPublisher.publish(outbox);
		}
	}

}
