package store.nightmarket.domain.item.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasket;

public class CancelManyPurchaseProductItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private List<ProductVariant> productVariantList;
        private ShoppingBasket shoppingBasket;

    }

    @Getter
    @Builder
    public static class Event {

        private List<ProductVariant> productVariantList;

    }

}
