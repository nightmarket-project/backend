package store.nightmarket.application.apporder.out.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.mapper.ProductVariantMapper;
import store.nightmarket.application.apporder.out.ReadProductVariantPort;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.persistence.persistorder.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadProductVariantJpaAdapter implements ReadProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public Optional<ProductVariant> read(ProductVariantId id) {
		return productVariantRepository.findById(id.getId())
			.map(ProductVariantMapper::toDomain);
	}

	@Override
	public List<ProductVariant> readByIdList(List<ProductVariantId> idList) {
		return productVariantRepository.findByIdList(idList.stream()
			.map(ProductVariantId::getId)
			.toList()
		).stream()
			.map(ProductVariantMapper::toDomain)
			.toList();

	}

}
