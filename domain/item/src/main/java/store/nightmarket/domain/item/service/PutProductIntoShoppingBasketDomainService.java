package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.PutProductIntoShoppingBasketDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

@Component
public class PutProductIntoShoppingBasketDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();
		UserId userId = input.getUserId();
		Quantity quantity = input.getQuantity();

		shoppingBasketProduct.changeQuantity(userId, quantity);

		return Event.builder()
			.shoppingBasketProduct(shoppingBasketProduct)
			.build();
	}

}
