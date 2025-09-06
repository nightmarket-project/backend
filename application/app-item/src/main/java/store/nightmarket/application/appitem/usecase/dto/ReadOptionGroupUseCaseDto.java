package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;

public class ReadOptionGroupUseCaseDto {

	@Builder
	public record Output(
		List<OptionGroupDto> optionGroupDtoList
	) {

	}

}
