package store.nightmarket.application.appitem.usecase.cart.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ModifyShoppingBasketQuantityUseCaseDto {

	@Builder
	public record Input(
		ShoppingBasketProductId shoppingBasketProductId,
		UserId userId,
		Quantity quantity
	) {

	}

}
