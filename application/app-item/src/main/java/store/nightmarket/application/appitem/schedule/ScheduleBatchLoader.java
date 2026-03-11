package store.nightmarket.application.appitem.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.config.SchedulingProperties;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleBatchLoader {

	private final SchedulingProperties schedulingProperties;
	private final ChunkTransactionManager chunkTransactionManager;

	@Scheduled(cron = "${scheduling.batch-cron}")
	@SchedulerLock(
		name = "schedule-batch-loader",
		lockAtMostFor = "PT2M",
		lockAtLeastFor = "PT30S"
	)
	public void loadBatchSchedules() {
		LocalDateTime endOfPeriod = LocalDateTime.now().plusHours(schedulingProperties.getBatchPeriod());
		int chunkSize = schedulingProperties.getBatchChunkSize();
		int totalLoaded = 0;

		while (true) {
			List<SchedulePost> chunk = chunkTransactionManager.loadChunkInTransaction(endOfPeriod, chunkSize);
			totalLoaded += chunk.size();

			if (chunk.size() < chunkSize) {
				break;
			}
		}

		if (totalLoaded > 0) {
			log.info("Batch loaded {} schedules into memory (upperBound={})", totalLoaded, endOfPeriod);
		}
	}

}