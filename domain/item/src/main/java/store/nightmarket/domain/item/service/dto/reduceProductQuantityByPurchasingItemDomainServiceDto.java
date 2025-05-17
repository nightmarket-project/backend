package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;

public class reduceProductQuantityByPurchasingItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final ProductItem productItem;
        private final UserBuyProductItem buyProductItem;
    }

    @Getter
    @Builder
    public static class Event {
        private final UserBuyProductItem buyProductItem;
    }

}
