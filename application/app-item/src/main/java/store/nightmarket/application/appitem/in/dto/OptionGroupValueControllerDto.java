package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record OptionGroupValueControllerDto(
	OptionGroupControllerDto optionGroupControllerDto,
	List<OptionValueControllerDto> optionValueControllerDtoList
) {

}
