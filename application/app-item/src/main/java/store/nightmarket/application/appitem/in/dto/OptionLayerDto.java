package store.nightmarket.application.appitem.in.dto;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionValueId;

@Getter
public abstract class OptionLayerDto {

	private final OptionValueId optionValueId;
	private final Name optionValueName;

	public OptionLayerDto(
		OptionValueId optionValueId,
		Name optionValueName
	) {
		this.optionValueId = optionValueId;
		this.optionValueName = optionValueName;
	}

}
