package store.nightmarket.application.appitem.usecase.option.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.option.mapper.dto.OptionGroupAdapterDto;

public class ReadOptionGroupUseCaseDto {

	@Builder
	public record Output(
		List<OptionGroupAdapterDto> optionGroupAdapterDtoList
	) {

	}

}
