package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Input;

public class ChangeProductQuantityToShoppingBasketItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBasket shoppingBasket = input.getShoppingBasket();
        ShoppingBaseketProduct shoppingBaseketProduct = input.getShoppingBaseketProduct();
        Quantity quantity = input.getQuantity();

        shoppingBasket.changeProductQuantity(shoppingBaseketProduct, quantity);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
