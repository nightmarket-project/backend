package store.nightmarket.application.appitem.out.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.mapper.SchedulePostMapper;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.SchedulePostState;
import store.nightmarket.persistence.persistitem.repository.SchedulePostRepository;

@Component
@RequiredArgsConstructor
public class ReadSchedulePostJpaAdapter implements ReadSchedulePostPort {

	private final SchedulePostRepository schedulePostRepository;

	@Override
	public Optional<SchedulePost> read(SchedulePostId id) {
		return schedulePostRepository.findById(id.getId())
			.map(SchedulePostMapper::toDomain);
	}

	@Override
	public List<SchedulePost> readReadyChunk(LocalDateTime endOfPeriod, int limit) {
		return schedulePostRepository.findReadyChunk(
				SchedulePostState.READY,
				LocalDateTime.now(),
				endOfPeriod,
				PageRequest.of(0, limit)
			)
			.stream()
			.map(SchedulePostMapper::toDomain)
			.toList();
	}

	@Override
	public List<SchedulePost> readScheduledChunk(LocalDateTime endOfPeriod, int limit) {
		return schedulePostRepository.findReadyChunk(
				SchedulePostState.SCHEDULED,
				LocalDateTime.now(),
				endOfPeriod,
				PageRequest.of(0, limit)
			)
			.stream()
			.map(SchedulePostMapper::toDomain)
			.toList();
	}

}
