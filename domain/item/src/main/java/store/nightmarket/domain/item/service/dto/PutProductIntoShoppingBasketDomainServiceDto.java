package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class PutProductIntoShoppingBasketDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private final ShoppingBasketProduct shoppingBasketProduct;
		private final UserId userId;
		private final Quantity quantity;

	}

	@Getter
	@Builder
	public static class Event {

		private final ShoppingBasketProduct shoppingBasketProduct;

	}

}
