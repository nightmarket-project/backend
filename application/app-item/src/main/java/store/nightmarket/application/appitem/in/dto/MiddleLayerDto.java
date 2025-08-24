package store.nightmarket.application.appitem.in.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionValueId;

@Getter
public class MiddleLayerDto extends OptionLayerDto {

	private final List<OptionLayerDto> optionLayerDtoList;

	public MiddleLayerDto(
		OptionValueId optionValueId,
		Name optionValueName,
		List<OptionLayerDto> optionLayerDtoList
	) {
		super(
			optionValueId,
			optionValueName
		);
		this.optionLayerDtoList = new ArrayList<>(optionLayerDtoList);
	}

}
