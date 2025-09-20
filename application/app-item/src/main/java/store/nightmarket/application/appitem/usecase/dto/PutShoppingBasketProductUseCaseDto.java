package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class PutShoppingBasketProductUseCaseDto {

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
