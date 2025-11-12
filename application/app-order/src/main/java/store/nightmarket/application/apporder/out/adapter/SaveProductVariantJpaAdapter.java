package store.nightmarket.application.apporder.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.mapper.ProductVariantMapper;
import store.nightmarket.application.apporder.out.SaveProductVariantPort;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.persistence.persistorder.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class SaveProductVariantJpaAdapter implements SaveProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public void save(ProductVariant productVariant) {
		productVariantRepository.save(ProductVariantMapper.toEntity(productVariant));
	}

}
