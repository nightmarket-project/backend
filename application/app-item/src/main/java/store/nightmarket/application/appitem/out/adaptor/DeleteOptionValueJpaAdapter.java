package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;

@Component
@RequiredArgsConstructor
public class DeleteOptionValueJpaAdapter implements DeleteOptionValuePort {

	private final OptionValueRepository optionValueRepository;

	@Override
	public void delete(OptionValueId optionValueId) {
		optionValueRepository.deleteById(optionValueId.getId());
	}

	@Override
	public void deleteByOptionGroupId(OptionGroupId optionGroupId) {
		optionValueRepository.deleteByOptionGroupId(optionGroupId.getId());
	}

}
