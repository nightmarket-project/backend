package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;

public class DeleteShoppingBasketProductUseCaseDto {

	@Builder
	public record Input(
		ShoppingBasketProductId shoppingBasketProductId,
		UserId userId
	) {

	}

}
