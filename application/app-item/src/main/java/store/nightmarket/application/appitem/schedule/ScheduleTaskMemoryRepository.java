package store.nightmarket.application.appitem.schedule;

import static store.nightmarket.application.appitem.constant.Constant.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleTaskMemoryRepository {

	private final ConcurrentHashMap<String, ScheduledFuture<?>> repository = new ConcurrentHashMap<>();

	public void save(SchedulePostId schedulePostId, ScheduledFuture<?> scheduledTask) {
		repository.put(getKey(schedulePostId), scheduledTask);
	}

	public void delete(SchedulePostId schedulePostId) {
		ScheduledFuture<?> scheduledFuture = repository.get(getKey(schedulePostId));
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
			repository.remove(getKey(schedulePostId));
			log.info("Schedule Canceled: {}", schedulePostId);
		}
	}

	private String getKey(SchedulePostId schedulePostId) {
		return SCHEDULED_TASK + schedulePostId.getId().toString();
	}

}
