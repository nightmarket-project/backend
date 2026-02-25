package store.nightmarket.application.appoutbox.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appoutbox.out.ReadOutboxPort;
import store.nightmarket.application.appoutbox.out.SaveOutboxPort;
import store.nightmarket.domain.outbox.model.Outbox;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxChunkProcessor {

	private final ReadOutboxPort readOutboxPort;
	private final SaveOutboxPort saveOutboxPort;
	private final KafkaMessagePublisher kafkaMessagePublisher;

	@Transactional
	public boolean process(int chunkSize) {
		List<Outbox> targets = readOutboxPort.readPublishTarget(chunkSize);

		if (targets.isEmpty())
			return false;

		for (Outbox outbox : targets) {
			if (kafkaMessagePublisher.publish(outbox)) {
				outbox.publish();
			} else {
				outbox.fail();
			}
			saveOutboxPort.saveWithId(outbox);
		}

		return targets.size() == chunkSize;
	}

}