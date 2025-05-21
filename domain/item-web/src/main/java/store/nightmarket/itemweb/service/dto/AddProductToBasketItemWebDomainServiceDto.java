package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemweb.model.ShoppingBasket;

public class AddProductToBasketItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final ProductItem productItem;
        private final UserBuyProductItem userBuyProductItem;
        private final ShoppingBasket shoppingBasket;
    }

    @Getter
    @Builder
    public static class Event {
        private final ShoppingBasket shoppingBasket;
    }

}
