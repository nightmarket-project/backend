package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBaseketProduct;

public class RemoveProductFromShoppingBasketItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final ShoppingBaseketProduct shoppingBaseketProduct;
        private final ShoppingBasket shoppingBasket;

    }

    @Getter
    @Builder
    public static class Event {

        private final ShoppingBasket shoppingBasket;

    }

}
