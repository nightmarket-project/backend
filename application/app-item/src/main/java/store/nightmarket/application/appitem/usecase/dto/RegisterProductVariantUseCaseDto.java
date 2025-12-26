package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class RegisterProductVariantUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		UserId userId,
		String SKUCode,
		Quantity quantity,
		List<OptionCombination> optionCombinationList
	) {

	}

	@Builder
	public record OptionCombination(
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {

	}

}
