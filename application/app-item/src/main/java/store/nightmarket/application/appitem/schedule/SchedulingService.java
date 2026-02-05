package store.nightmarket.application.appitem.schedule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {

	private final TaskScheduler taskScheduler;
	private final ScheduleTaskMemoryRepository scheduleTaskMemoryRepository;

	public <T> void addSchedule(T targert, Consumer<T> action, LocalDateTime executionTime,
		SchedulePostId schedulePostId) {
		SchedulingTask<T> task = new SchedulingTask<>(targert, action);
		Instant instant = getInstant(executionTime);
		ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, instant);
		scheduleTaskMemoryRepository.save(schedulePostId, scheduledTask);
		log.info("Schedule Added: {}", schedulePostId);
	}

	public void cancelSchedule(SchedulePostId schedulePostId) {
		scheduleTaskMemoryRepository.delete(schedulePostId);
	}

	private Instant getInstant(LocalDateTime executionTime) {
		return executionTime.atZone(ZoneId.systemDefault()).toInstant();
	}

}
