package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.CancelScheduleUseCaseDto.*;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.schedule.event.ScheduleCanceledEvent;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Service
@RequiredArgsConstructor
public class CancelScheduleUseCase implements BaseUseCase<Input, Void> {

	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = readSchedulePostPort.readOrThrow(input.schedulePostId());

		schedulePost.cancel();

		saveSchedulePostPort.save(schedulePost);

		eventPublisher.publishEvent(ScheduleCanceledEvent.builder()
			.schedulePostId(input.schedulePostId())
			.build()
		);
		return null;
	}

}
