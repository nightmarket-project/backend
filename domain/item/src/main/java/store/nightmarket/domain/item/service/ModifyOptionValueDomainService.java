package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.ModifyOptionValueDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.OptionValue;

@Component
public class ModifyOptionValueDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		OptionValue optionValue = input.optionValue();

		optionValue.edit(
			input.name(),
			input.price(),
			input.order()
		);

		return Event.builder()
			.optionValue(optionValue)
			.build();
	}

}
