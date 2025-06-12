package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.UserBuyItemGroup;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemweb.service.dto.RemoveProductFromBasketItemWebDomainServiceDto.*;

public class RemoveProductFromBasketItemWebDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        UserBuyItemGroup userBuyItemGroup = input.getUserBuyItem();
        Cart cart = input.getCart();

        if(cart.hasProductInBasket(userBuyItemGroup)) {
            cart.removeProductFromBasket(userBuyItemGroup);
        } else {
            throw new ItemWebException("장바구니는 해당 아이템을 가지고 있지 않습니다.");
        }

        return Event.builder()
                .cart(cart)
                .build();
    }

}
