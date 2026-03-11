package store.nightmarket.application.appoutbox.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appoutbox.out.ReadOutboxPort;
import store.nightmarket.application.appoutbox.out.SaveOutboxPort;
import store.nightmarket.application.appoutbox.strategy.OutboxEventRegistry;
import store.nightmarket.domain.outbox.model.Outbox;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxChunkProcessor {

	private final ReadOutboxPort readOutboxPort;
	private final SaveOutboxPort saveOutboxPort;
	private final KafkaMessagePublisher kafkaMessagePublisher;
	private final OutboxEventRegistry outboxEventRegistry;

	@Transactional
	public boolean process(int chunkSize) {
		List<String> supportedTypes = List.copyOf(outboxEventRegistry.getSupportedEventTypes());
		List<Outbox> targets = readOutboxPort.readPublishTarget(chunkSize, supportedTypes);

		if (targets.isEmpty())
			return false;

		for (Outbox outbox : targets) {
			if (kafkaMessagePublisher.publish(outbox)) {
				outbox.publish();
			} else {
				outbox.failOrDead();
			}
			saveOutboxPort.saveWithId(outbox);
		}

		return targets.size() == chunkSize;
	}

}