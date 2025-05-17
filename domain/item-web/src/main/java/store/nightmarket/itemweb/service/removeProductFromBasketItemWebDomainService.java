package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.model.ShoppingBasket;
import store.nightmarket.itemweb.service.dto.removeProductFromBasketItemWebDomainServiceDto.*;

public class removeProductFromBasketItemWebDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        UserBuyProductItem userBuyProductItem = input.getUserBuyProductItem();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        if(shoppingBasket.hasProductInBasket(userBuyProductItem)) {
            shoppingBasket.removeProductFromBasket(userBuyProductItem);
        } else {
            throw new ItemWebException("장바구니는 해당 아이템을 가지고 있지 않습니다.");
        }

        return Event.builder()
                .shoppingBasket(shoppingBasket)
                .build();
    }

}
