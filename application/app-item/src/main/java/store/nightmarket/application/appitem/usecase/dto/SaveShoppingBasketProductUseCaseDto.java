package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

public class SaveShoppingBasketProductUseCaseDto {

	@Builder
	public record Input(
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price price,
		Quantity quantity
	) {

	}

}
