package store.nightmarket.itemweb.service;

import static store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Input;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;

public class AddProductToShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBaseketProduct shoppingBaseketProduct = input.getShoppingBaseketProduct();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        shoppingBasket.add(shoppingBaseketProduct);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
