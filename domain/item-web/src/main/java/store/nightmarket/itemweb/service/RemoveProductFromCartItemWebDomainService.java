package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemweb.service.dto.RemoveProductFromCartItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.RemoveProductFromCartItemWebDomainServiceDto.Input;

public class RemoveProductFromCartItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        CartProduct cartProduct = input.getCartProduct();
        Cart cart = input.getCart();

        cart.remove(cartProduct);

        return Event.builder()
            .cart(cart)
            .build();
    }

}
