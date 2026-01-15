package store.nightmarket.application.appitem.out.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class DeleteProductVariantJpaAdapter implements DeleteProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public void delete(ProductVariantId productVariantId) {
		productVariantRepository.deleteById(productVariantId.getId());
	}

	@Override
	public void deleteAll(List<ProductVariantId> productVariantIdList) {
		productVariantRepository.deleteAllById(
			productVariantIdList.stream()
				.map(ProductVariantId::getId)
				.toList()
		);
	}

}
