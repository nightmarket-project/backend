package store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor;

import static store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.SchedulePostActionStrategy;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.SchedulePostActionStrategyRegistry;
import store.nightmarket.common.application.component.BaseComponent;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Component
@RequiredArgsConstructor
public class SchedulePostStrategyExecutor implements BaseComponent<Input, Void> {

	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulePostActionStrategyRegistry schedulePostActionStrategyRegistry;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = readSchedulePostPort.readOrThrow(input.schedulePostId());

		SchedulePostActionStrategy strategy = schedulePostActionStrategyRegistry.get(schedulePost.getType());

		strategy.execute(schedulePost);

		schedulePost.done();
		saveSchedulePostPort.save(schedulePost);
		return null;
	}

}