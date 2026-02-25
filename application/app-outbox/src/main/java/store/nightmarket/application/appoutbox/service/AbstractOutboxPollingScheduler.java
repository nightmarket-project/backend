package store.nightmarket.application.appoutbox.service;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOutboxPollingScheduler {

	@Value("${polling.scheduler.maxIteration}")
	private int maxIteration;

	@Value("${polling.scheduler.chunkSize}")
	private int chunkSize;

	private final OutboxChunkProcessor outboxChunkProcessor;

	public void pollOutbox() {
		log.info("[Outbox] Polling started");
		int iteration = 0;

		while (iteration++ < maxIteration) {
			boolean hasMore = outboxChunkProcessor.process(chunkSize);
			if (!hasMore) {
				log.info("[Outbox] No more targets. Stopping after {} iterations", iteration);
				break;
			}
		}

		log.info("[Outbox] Polling finished. total iterations={}", iteration);
	}
}