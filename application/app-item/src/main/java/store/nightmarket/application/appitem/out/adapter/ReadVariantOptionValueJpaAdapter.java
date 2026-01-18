package store.nightmarket.application.appitem.out.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.application.appitem.out.mapper.VariantOptionValueMapper;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;
import store.nightmarket.persistence.persistitem.repository.VariantOptionValueRepository;

@Component
@RequiredArgsConstructor
public class ReadVariantOptionValueJpaAdapter implements ReadVariantOptionValuePort {

	private final VariantOptionValueRepository variantOptionValueRepository;

	@Override
	public Optional<VariantOptionValue> read(VariantOptionValueId variantOptionValueId) {
		return variantOptionValueRepository.findById(variantOptionValueId.getId())
			.map(VariantOptionValueMapper::toDomain);
	}

	@Override
	public List<ProductVariantId> readProductVariantIdsByOptionGroupId(OptionGroupId optionGroupId) {
		return variantOptionValueRepository.findProductVariantIdsByOptionGroupId(optionGroupId.getId())
			.stream()
			.map(ProductVariantId::new)
			.toList();
	}

	@Override
	public List<ProductVariantId> readProductVariantIdsByOptionValueId(OptionValueId optionValueId) {
		return variantOptionValueRepository.findProductVariantIdsByOptionValueId(optionValueId.getId())
			.stream()
			.map(ProductVariantId::new)
			.toList();
	}

	@Override
	public List<ProductVariantId> readProductVariantIdsByProductId(ProductId productId) {
		return variantOptionValueRepository.findProductVariantIdsByproductId(productId.getId())
			.stream()
			.map(ProductVariantId::new)
			.toList();
	}

	@Override
	public List<VariantOptionValue> readByIdProductVariantId(ProductVariantId productVariantId) {
		return variantOptionValueRepository.findByIdProductVariantId(productVariantId.getId())
			.stream()
			.map(VariantOptionValueMapper::toDomain)
			.toList();
	}

}
