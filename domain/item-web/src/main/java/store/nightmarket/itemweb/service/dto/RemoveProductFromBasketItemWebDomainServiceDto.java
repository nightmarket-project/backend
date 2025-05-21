package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemweb.model.ShoppingBasket;

public class RemoveProductFromBasketItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final UserBuyProductItem userBuyProductItem;
        private final ShoppingBasket shoppingBasket;
    }

    @Getter
    @Builder
    public static class Event {
        private final ShoppingBasket shoppingBasket;
    }

}
