package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class SaveShoppingBasketProductDto {

	@Builder
	public record Request(
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price price,
		Quantity quantity
	) {

	}
}
