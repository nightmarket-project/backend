package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ProductVariantMapper;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.ProductVariantDto;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadProductVariantAdaptor implements ReadProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public List<ProductVariant> read(UUID id) {
		return productVariantRepository.findByProductId(id).stream()
			.map(ProductVariantMapper::toDomain)
			.toList();
	}

	@Override
	public List<ProductVariantDto> readFetchVariantOptionValue(UUID id) {
		return productVariantRepository.findByProductId(id).stream()
			.map(ProductVariantDto::toDomain)
			.toList();
	}

}
