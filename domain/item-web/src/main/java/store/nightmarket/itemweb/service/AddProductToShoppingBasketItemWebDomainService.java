package store.nightmarket.itemweb.service;

import static store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Input;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;

public class AddProductToShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        shoppingBasket.add(shoppingBasketProduct);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
