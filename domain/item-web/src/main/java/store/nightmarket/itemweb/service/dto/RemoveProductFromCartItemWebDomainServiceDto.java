package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;

public class RemoveProductFromCartItemWebDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final CartProduct cartProduct;
        private final Cart cart;

    }

    @Getter
    @Builder
    public static class Event {

        private final Cart cart;

    }

}
