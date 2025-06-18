package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToCartItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToCartItemWebDomainServiceDto.Input;

public class ChangeProductQuantityToCartItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Cart cart = input.getCart();
        CartProduct cartProduct = input.getCartProduct();
        Quantity quantity = input.getQuantity();

        cart.changeProductQuantity(cartProduct, quantity);

        return Event.builder()
            .cart(cart)
            .build();
    }

}
