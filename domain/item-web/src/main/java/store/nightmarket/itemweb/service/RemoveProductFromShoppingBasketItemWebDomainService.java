package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Input;

public class RemoveProductFromShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        shoppingBasket.remove(shoppingBasketProduct);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
