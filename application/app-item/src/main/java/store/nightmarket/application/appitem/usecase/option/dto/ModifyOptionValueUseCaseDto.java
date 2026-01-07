package store.nightmarket.application.appitem.usecase.option.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyOptionValueUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId,
		UserId userId,
		Name name,
		Price price,
		int displayOrder
	) {

	}

}
