package store.nightmarket.application.appuser.outbox;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appoutbox.service.AbstractOutboxPollingScheduler;
import store.nightmarket.application.appoutbox.service.OutboxChunkProcessor;

@Slf4j
@Component
public class OutboxPollingScheduler extends AbstractOutboxPollingScheduler {

	public OutboxPollingScheduler(OutboxChunkProcessor outboxChunkProcessor) {
		super(outboxChunkProcessor);
	}

	@Scheduled(cron = "${schedules.outbox.cron}")
	@SchedulerLock(name = "user-outbox-polling-scheduler", lockAtLeastFor = "9s", lockAtMostFor = "9s")
	@Override
	public void pollOutbox() {
		log.info("[User Application]: outbox-polling-scheduler");
		super.pollOutbox();
	}

}
