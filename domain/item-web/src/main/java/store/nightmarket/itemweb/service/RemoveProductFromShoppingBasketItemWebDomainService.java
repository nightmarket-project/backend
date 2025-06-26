package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Input;

public class RemoveProductFromShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBaseketProduct shoppingBaseketProduct = input.getShoppingBaseketProduct();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        shoppingBasket.remove(shoppingBaseketProduct);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
