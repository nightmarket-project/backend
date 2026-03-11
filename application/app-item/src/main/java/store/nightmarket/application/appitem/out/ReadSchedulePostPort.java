package store.nightmarket.application.appitem.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;

public interface ReadSchedulePostPort {

	Optional<SchedulePost> read(SchedulePostId id);

	default SchedulePost readOrThrow(SchedulePostId id) {
		return read(id)
			.orElseThrow(() -> new ProductPostException("Not Found SchedulePost"));
	}

	List<SchedulePost> readReady(LocalDateTime endOfPeriod);

	List<SchedulePost> readScheduled(LocalDateTime endOfPeriod);

	List<SchedulePost> readReadyChunk(LocalDateTime endOfPeriod, int limit);

	List<SchedulePost> readScheduledChunk(LocalDateTime endOfPeriod, int limit);
}
