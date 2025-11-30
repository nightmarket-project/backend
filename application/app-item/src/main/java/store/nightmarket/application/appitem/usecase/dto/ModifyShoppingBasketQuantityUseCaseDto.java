package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class ModifyShoppingBasketQuantityUseCaseDto {

	@Builder
	public record Input(
		ShoppingBasketProductId shoppingBasketProductId,
		UserId userId,
		Quantity quantity
	) {

	}

}
