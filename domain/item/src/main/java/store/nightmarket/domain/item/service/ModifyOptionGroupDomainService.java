package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.ModifyOptionGroupDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.OptionGroup;

@Component
public class ModifyOptionGroupDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		OptionGroup optionGroup = input.optionGroup();

		optionGroup.edit(input.name(), input.order());

		return Event.builder()
			.optionGroup(optionGroup)
			.build();
	}

}
