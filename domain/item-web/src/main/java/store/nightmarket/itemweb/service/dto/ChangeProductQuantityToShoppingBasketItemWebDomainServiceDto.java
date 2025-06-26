package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.valueobject.Quantity;

public class ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final ShoppingBasket shoppingBasket;
        private final ShoppingBaseketProduct shoppingBaseketProduct;
        private final Quantity quantity;

    }

    @Getter
    @Builder
    public static class Event {
        private final ShoppingBasket shoppingBasket;
    }

}
