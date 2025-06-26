package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Input;

public class ChangeProductQuantityToShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBasket shoppingBasket = input.getShoppingBasket();
        ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();
        Quantity quantity = input.getQuantity();

        shoppingBasket.changeProductQuantity(shoppingBasketProduct, quantity);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
