package store.nightmarket.application.appitem.schedule;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.ExecuteSchedulePostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Component
@RequiredArgsConstructor
public class RescheduleService {

	private final SchedulingService schedulingService;
	private final ReadSchedulePostPort readSchedulePostPort;
	private final ExecuteSchedulePostUseCase executeSchedulePostUseCase;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void restoreSchedules() {
		List<SchedulePost> schedules = readSchedulePostPort.readAllByReady();

		schedules.forEach(schedule ->
			schedulingService.addSchedule(
				ExecuteSchedulePostUseCaseDto.Input.builder()
					.schedulePostId(schedule.getSchedulePostId())
					.build(),
				executeSchedulePostUseCase::execute,
				schedule.getScheduledAt(),
				schedule.getSchedulePostId()
			)
		);

	}

}
