package store.nightmarket.application.appitem.out.option.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.SaveOptionValuePort;
import store.nightmarket.application.appitem.out.option.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.out.option.mapper.OptionValueMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;

@Component
@RequiredArgsConstructor
public class SaveOptionValueJpaAdapter implements SaveOptionValuePort {

	private final OptionValueRepository optionValueRepository;

	@Override
	public void save(OptionValue optionValue, OptionGroup optionGroup) {
		optionValueRepository.save(
			OptionValueMapper.toEntity(
				optionValue,
				OptionGroupMapper.toEntity(optionGroup)
			)
		);
	}

}
