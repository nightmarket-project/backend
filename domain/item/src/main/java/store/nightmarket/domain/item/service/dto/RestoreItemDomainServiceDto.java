package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;

public class RestoreItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final ProductVariant productVariant;
        private final ShoppingBasketProduct shoppingBasketProduct;

    }

    @Getter
    @Builder
    public static class Event {

        private final ProductVariant productVariant;

    }

}
