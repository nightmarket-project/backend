package store.nightmarket.application.appitem.out.adapter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadPreemptedProductVariantPort;
import store.nightmarket.application.appitem.out.mapper.PreemptedProductVariantMapper;
import store.nightmarket.domain.item.model.PreemptedProductVariant;
import store.nightmarket.domain.item.model.id.PreemptedProductVariantId;
import store.nightmarket.persistence.persistitem.repository.PreemptedProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadPreemptedProductVariantJpaAdapter implements ReadPreemptedProductVariantPort {

	private final PreemptedProductVariantRepository preemptedProductVariantRepository;

	@Override
	public Optional<PreemptedProductVariant> read(PreemptedProductVariantId id) {
		return preemptedProductVariantRepository.findById(id.getId())
			.map(PreemptedProductVariantMapper::toDomain);
	}

	@Override
	public Long readPreemptedQuantity(UUID productVariantId, LocalDateTime now) {
		return preemptedProductVariantRepository.getPreemptedQuantity(productVariantId, now);
	}

}
