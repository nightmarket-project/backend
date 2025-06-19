package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemcore.valueobject.Quantity;

public class ChangeProductQuantityToCartItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final Cart cart;
        private final CartProduct cartProduct;
        private final Quantity quantity;

    }

    @Getter
    @Builder
    public static class Event {
        private final Cart cart;
    }

}
