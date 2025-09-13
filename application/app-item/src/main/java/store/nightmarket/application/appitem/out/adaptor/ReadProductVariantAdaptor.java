package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadProductVariantAdaptor implements ReadProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId productId) {
		return productVariantRepository.findByProductId(productId.getId()).stream()
			.map(ProductVariantAdapterDto::toDomain)
			.toList();
	}

}
