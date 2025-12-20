package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SavePreemptionPort;
import store.nightmarket.application.appitem.out.mapper.PreemptionMapper;
import store.nightmarket.domain.item.model.Preemption;
import store.nightmarket.persistence.persistitem.repository.PreemptionRepository;

@Component
@RequiredArgsConstructor
public class SavePreemptionJpaAdapter implements SavePreemptionPort {

	private final PreemptionRepository preemptionRepository;

	@Override
	public void save(Preemption preemption) {
		preemptionRepository.save(PreemptionMapper.toEntity(preemption));
	}

}
