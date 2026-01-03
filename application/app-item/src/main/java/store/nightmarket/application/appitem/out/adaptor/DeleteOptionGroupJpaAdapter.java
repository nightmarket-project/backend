package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;

@Component
@RequiredArgsConstructor
public class DeleteOptionGroupJpaAdapter implements DeleteOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;

	@Override
	public void deleteById(OptionGroupId optionGroupId) {
		optionGroupRepository.deleteById(optionGroupId.getId());
	}

}
