package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.service.dto.ChangeShoppingBasketProductQuantityItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeShoppingBasketProductQuantityItemWebDomainServiceDto.Input;

public class SetShoppingBasketProductQuantityItemWebDomainService
    implements BaseDomainService<Input, Event> {

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
