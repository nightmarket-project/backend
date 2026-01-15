package store.nightmarket.application.appitem.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.mapper.ProductVariantMapper;
import store.nightmarket.application.appitem.out.SaveProductVariantPort;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class SaveProductVariantJpaAdapter implements SaveProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public void save(ProductVariant productVariant) {
		productVariantRepository.save(ProductVariantMapper.toEntity(productVariant));
	}
}
