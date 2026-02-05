package store.nightmarket.application.appitem.schedule;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.schedule.event.ScheduleCanceledEvent;

@Component
@RequiredArgsConstructor
public class ScheduleEventListener {

	private final SchedulingService schedulingService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleScheduleCanceled(ScheduleCanceledEvent event) {
		schedulingService.cancelSchedule(event.schedulePostId());
	}

}
