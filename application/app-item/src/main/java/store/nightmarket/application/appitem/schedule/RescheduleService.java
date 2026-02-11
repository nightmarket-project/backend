package store.nightmarket.application.appitem.schedule;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Component
@RequiredArgsConstructor
public class RescheduleService {

	private final SchedulingService schedulingService;
	private final ReadSchedulePostPort readSchedulePostPort;
	private final SchedulePostStrategyExecutor schedulePostStrategyExecutor;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	@SchedulerLock(
		name = "reschedule-posts",
		lockAtMostFor = "PT10S",
		lockAtLeastFor = "PT10S"
	)
	public void restoreSchedulePosts() {
		List<SchedulePost> schedules = readSchedulePostPort.readAllByReady();

		schedules.forEach(schedule ->
			schedulingService.addSchedule(
				SchedulePostStrategyExecutorDto.Input.builder()
					.schedulePostId(schedule.getSchedulePostId())
					.build(),
				schedulePostStrategyExecutor::execute,
				schedule.getScheduledAt(),
				schedule.getSchedulePostId()
			)
		);
	}

}
