package store.nightmarket.application.appitem.usecase.option.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterOptionValueUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		OptionGroupId optionGroupId,
		UserId userId,
		Name name,
		Price price,
		int displayOrder
	) {

	}

}
