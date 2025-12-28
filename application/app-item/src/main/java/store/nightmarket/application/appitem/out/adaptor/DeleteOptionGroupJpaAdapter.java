package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.mapper.OptionGroupMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;

@Component
@RequiredArgsConstructor
public class DeleteOptionGroupJpaAdapter implements DeleteOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;
	private final OptionValueRepository optionValueRepository;

	@Override
	public void delete(OptionGroup optionGroup) {
		OptionGroupEntity optionGroupEntity = OptionGroupMapper.toEntity(optionGroup);
		List<OptionValueEntity> optionValueEntityList
			= optionValueRepository.findByOptionGroupEntity(optionGroupEntity);

		optionGroupRepository.delete(optionGroupEntity);
		optionValueRepository.deleteAll(optionValueEntityList);
	}

}
