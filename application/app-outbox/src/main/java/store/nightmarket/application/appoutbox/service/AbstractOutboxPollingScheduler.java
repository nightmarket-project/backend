package store.nightmarket.application.appoutbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOutboxPollingScheduler {

	private static final int MAX_ITERATION = 50;
	private static final int CHUNK_SIZE = 100;

	private final OutboxChunkProcessor outboxChunkProcessor;

	public void pollOutbox() {
		log.info("[Outbox] Polling started");
		int iteration = 0;

		while (iteration++ < MAX_ITERATION) {
			boolean hasMore = outboxChunkProcessor.process(CHUNK_SIZE);
			if (!hasMore) {
				log.info("[Outbox] No more targets. Stopping after {} iterations", iteration);
				break;
			}
		}

		log.info("[Outbox] Polling finished. total iterations={}", iteration);
	}
}