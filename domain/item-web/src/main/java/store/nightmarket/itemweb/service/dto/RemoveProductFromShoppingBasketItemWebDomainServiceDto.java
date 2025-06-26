package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;

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
