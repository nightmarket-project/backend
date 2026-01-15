package store.nightmarket.application.appitem.out.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SavePreemptedProductVariantPort;
import store.nightmarket.application.appitem.out.mapper.PreemptedProductVariantMapper;
import store.nightmarket.domain.item.model.PreemptedProductVariant;
import store.nightmarket.persistence.persistitem.repository.PreemptedProductVariantRepository;

@Component
@RequiredArgsConstructor
public class SavePreemptedProductVariantJpaAdapter implements SavePreemptedProductVariantPort {

	private final PreemptedProductVariantRepository preemptedProductVariantRepository;

	@Override
	public void save(PreemptedProductVariant preemptedProductVariant) {
		preemptedProductVariantRepository.save(PreemptedProductVariantMapper.toEntity(preemptedProductVariant));
	}

	@Override
	public void saveAll(List<PreemptedProductVariant> preemptedProductVariantList) {
		preemptedProductVariantRepository.saveAll(
			preemptedProductVariantList.stream()
				.map(PreemptedProductVariantMapper::toEntity)
				.toList()
		);
	}

}
