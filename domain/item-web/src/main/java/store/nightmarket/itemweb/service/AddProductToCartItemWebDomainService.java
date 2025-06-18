package store.nightmarket.itemweb.service;

import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Input;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;

public class AddProductToCartItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        CartProduct cartProduct = input.getCartProduct();
        Cart cart = input.getCart();

        cart.add(cartProduct);

        return Event.builder()
            .cart(cart)
            .build();
    }

}
