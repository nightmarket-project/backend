package store.nightmarket.application.appitem.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.SchedulePostStrategyExecutor;
import store.nightmarket.application.appitem.usecase.post.schedule.strategy.executor.dto.SchedulePostStrategyExecutorDto;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Component
@RequiredArgsConstructor
public class ChunkTransactionManager {

	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final SchedulingService schedulingService;
	private final SchedulePostStrategyExecutor schedulePostStrategyExecutor;

	@Transactional
	public List<SchedulePost> loadChunkInTransaction(LocalDateTime endOfPeriod, int limit) {
		List<SchedulePost> chunk = readSchedulePostPort.readReadyChunk(endOfPeriod, limit);

		chunk.forEach(schedulePost -> {
			addToSchedule(schedulePost);
			schedulePost.schedule();
			saveSchedulePostPort.save(schedulePost);
		});

		return chunk;
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
