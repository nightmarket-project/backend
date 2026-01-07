package store.nightmarket.application.appitem.out.option.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.SaveOptionGroupPort;
import store.nightmarket.application.appitem.out.option.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.out.option.mapper.OptionValueMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;

@Component
@RequiredArgsConstructor
public class SaveOptionGroupJpaAdapter implements SaveOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;
	private final OptionValueRepository optionValueRepository;

	@Override
	public void save(OptionGroup optionGroup) {
		optionGroupRepository.save(OptionGroupMapper.toEntity(optionGroup));
	}

	@Override
	public void save(OptionGroup optionGroup, List<OptionValue> optionValueList) {
		OptionGroupEntity optionGroupEntity = OptionGroupMapper.toEntity(optionGroup);
		optionGroupRepository.save(optionGroupEntity);
		optionValueRepository.saveAll(
			optionValueList.stream()
				.map(optionValue -> OptionValueMapper.toEntity(optionValue, optionGroupEntity))
				.toList()
		);
	}
}
