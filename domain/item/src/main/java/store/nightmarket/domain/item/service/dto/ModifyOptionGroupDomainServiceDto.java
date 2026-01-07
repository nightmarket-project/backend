package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.valueobject.Name;

public class ModifyOptionGroupDomainServiceDto {

	@Builder
	public record Input(
		OptionGroup optionGroup,
		Name name,
		int order
	) {

	}

	@Builder
	public record Event(
		OptionGroup optionGroup
	) {

	}

}
