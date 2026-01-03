package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;
import store.nightmarket.persistence.persistitem.repository.VariantOptionValueRepository;

@Component
@RequiredArgsConstructor
public class DeleteVariantOptionValueJpaAdapter implements DeleteVariantOptionValuePort {

	private final VariantOptionValueRepository variantOptionValueRepository;

	@Override
	public void delete(VariantOptionValueId variantOptionValueId) {
		variantOptionValueRepository.deleteById(variantOptionValueId.getId());
	}

	@Override
	public void deleteByOptionGroupId(OptionGroupId optionGroupId) {
		variantOptionValueRepository.deleteAllByOptionGroupId(optionGroupId.getId());
	}

	@Override
	public void deleteByOptionValueId(OptionValueId optionValueId) {
		variantOptionValueRepository.deleteByOptionValueId(optionValueId.getId());
	}

}
