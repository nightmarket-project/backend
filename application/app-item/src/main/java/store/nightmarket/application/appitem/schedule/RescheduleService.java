package store.nightmarket.application.appitem.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.config.SchedulingProperties;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Slf4j
@Component
@RequiredArgsConstructor
public class RescheduleService {

	private final SchedulingProperties schedulingProperties;
	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulingService schedulingService;
	private final SchedulePostStrategyExecutor schedulePostStrategyExecutor;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	@SchedulerLock(
		name = "reschedule-posts",
		lockAtMostFor = "PT2M",
		lockAtLeastFor = "PT10S"
	)
	public void restoreSchedulePosts() {
		LocalDateTime endOfPeriod = LocalDateTime.now().plusHours(schedulingProperties.getBatchPeriod());

		int readyCount = restoreReady(endOfPeriod);
		int scheduledCount = restoreScheduled(endOfPeriod);

		log.info("RescheduleService completed: restored READY={}, SCHEDULED={}", readyCount, scheduledCount);
	}

	private int restoreReady(LocalDateTime endOfPeriod) {
		List<SchedulePost> readyList = readSchedulePostPort.readReady(endOfPeriod);

		readyList.forEach(schedulePost -> {
			addToSchedule(schedulePost);
			schedulePost.schedule();
			saveSchedulePostPort.save(schedulePost);
		});

		return readyList.size();
	}

	private int restoreScheduled(LocalDateTime endOfPeriod) {
		List<SchedulePost> scheduledList = readSchedulePostPort.readScheduled(endOfPeriod);

		scheduledList.forEach(this::addToSchedule);

		return scheduledList.size();
	}

	private void addToSchedule(SchedulePost schedule) {
		schedulingService.addSchedule(
			SchedulePostStrategyExecutorDto.Input.builder()
				.schedulePostId(schedule.getSchedulePostId())
				.build(),
			schedulePostStrategyExecutor::execute,
			schedule.getScheduledAt(),
			schedule.getSchedulePostId()
		);
	}

}
