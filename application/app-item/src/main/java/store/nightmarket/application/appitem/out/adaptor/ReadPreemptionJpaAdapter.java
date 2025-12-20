package store.nightmarket.application.appitem.out.adaptor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadPreemptionPort;
import store.nightmarket.application.appitem.out.mapper.PreemptionMapper;
import store.nightmarket.domain.item.model.Preemption;
import store.nightmarket.domain.item.model.id.PreemptionId;
import store.nightmarket.persistence.persistitem.repository.PreemptionRepository;

@Component
@RequiredArgsConstructor
public class ReadPreemptionJpaAdapter implements ReadPreemptionPort {

	private final PreemptionRepository preemptionRepository;

	@Override
	public Optional<Preemption> read(PreemptionId id) {
		return preemptionRepository.findById(id.getId())
			.map(PreemptionMapper::toDomain);
	}

	@Override
	public Long readPreemptedQuantity(UUID productVariantId, LocalDateTime now) {
		return preemptionRepository.getPreemptedQuantity(productVariantId, now);
	}

}
