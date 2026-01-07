package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyOptionValueDomainServiceDto {

	@Builder
	public record Input(
		OptionValue optionValue,
		Name name,
		Price price,
		int order
	) {

	}

	@Builder
	public record Event(
		OptionValue optionValue
	) {

	}

}
