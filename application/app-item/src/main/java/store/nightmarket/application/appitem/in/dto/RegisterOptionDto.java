package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.List;

import lombok.Builder;

public class RegisterOptionDto {

	@Builder
	public record Request(
		String name,
		int displayOrder,
		List<OptionValueDto> optionValueDtoList
	) {

	}

	@Builder
	public record OptionValueDto(
		String name,
		BigInteger price,
		int displayOrder
	) {

	}

}
