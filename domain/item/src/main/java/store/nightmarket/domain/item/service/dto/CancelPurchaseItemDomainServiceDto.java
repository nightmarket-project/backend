package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;

public class CancelPurchaseItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private ProductVariant productVariant;
        private ShoppingBasketProduct shoppingBasketProduct;

    }

    @Getter
    @Builder
    public static class Event {

        private ProductVariant productVariant;

    }


}
