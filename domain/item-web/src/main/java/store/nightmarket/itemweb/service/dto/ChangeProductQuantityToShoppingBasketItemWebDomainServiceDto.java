package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBaseketProduct;
import store.nightmarket.domain.item.valueobject.Quantity;

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
