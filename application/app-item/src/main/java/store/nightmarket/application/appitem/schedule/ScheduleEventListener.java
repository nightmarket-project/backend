package store.nightmarket.application.appitem.schedule;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.config.SchedulingProperties;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.event.SchedulePostCanceledEvent;
import store.nightmarket.application.appitem.schedule.event.SchedulePostCreatedEvent;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleEventListener {

	private final SchedulingService schedulingService;
	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulePostStrategyExecutor schedulePostStrategyExecutor;
	private final SchedulingProperties schedulingProperties;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleSchedulePostCreated(SchedulePostCreatedEvent event) {
		SchedulePost schedulePost = readSchedulePostPort.readOrThrow(event.schedulePostId());

		if (!isWithinPeriod(schedulePost.getScheduledAt())) {
			log.debug("SchedulePost is within period, will be loaded on schedule: id={}", event.schedulePostId());
			return;
		}

		schedulingService.addSchedule(
			SchedulePostStrategyExecutorDto.Input.builder()
				.schedulePostId(schedulePost.getSchedulePostId())
				.build(),
			schedulePostStrategyExecutor::execute,
			schedulePost.getScheduledAt(),
			schedulePost.getSchedulePostId()
		);

		schedulePost.schedule();
		saveSchedulePostPort.save(schedulePost);

		log.info("SchedulePost immediately loaded to memory: id={}", event.schedulePostId());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleSchedulePostCanceled(SchedulePostCanceledEvent event) {
		schedulingService.cancelSchedule(event.schedulePostId());
	}

	private boolean isWithinPeriod(LocalDateTime scheduledAt) {
		LocalDateTime upperBound = LocalDateTime.now().plusHours(schedulingProperties.getBatchPeriod());
		return !scheduledAt.isAfter(upperBound);
	}

}
