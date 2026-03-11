package store.nightmarket.application.appitem.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.out.mapper.SchedulePostMapper;
import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.persistence.persistitem.repository.SchedulePostRepository;

@Component
@RequiredArgsConstructor
public class SaveSchedulePostJpaAdapter implements SaveSchedulePostPort {

	private final SchedulePostRepository schedulePostRepository;

	@Override
	public void save(SchedulePost schedulePost) {
		schedulePostRepository.save(SchedulePostMapper.toEntity(schedulePost));
	}

}
