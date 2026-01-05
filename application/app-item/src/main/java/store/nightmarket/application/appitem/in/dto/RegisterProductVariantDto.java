package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class RegisterProductVariantDto {

	@Builder
	public record Request(
		String SKUCode,
		BigInteger quantity,
		List<OptionCombination> optionCombinationList
	) {

	}

	@Builder
	public record OptionCombination(
		UUID optionGroupId,
		UUID optionValueId
	) {

	}

}
