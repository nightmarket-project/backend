package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;

public class AddProductToShoppingBasketItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final ShoppingBasketProduct shoppingBasketProduct;
        private final ShoppingBasket shoppingBasket;

    }

    @Getter
    @Builder
    public static class Event {

        private final ShoppingBasket shoppingBasket;

    }

}
