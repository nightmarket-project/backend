package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ChangeShoppingBasketProductQuantityItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final ShoppingBasketProduct shoppingBasketProduct;
        private final Quantity quantity;

    }

    @Getter
    @Builder
    public static class Event {

        private final ShoppingBasketProduct shoppingBasketProduct;

    }

}
