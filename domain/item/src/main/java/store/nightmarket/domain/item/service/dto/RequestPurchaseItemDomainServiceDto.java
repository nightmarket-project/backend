package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserProductItem;

public class RequestPurchaseItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final ProductItem productItem;
        private final UserProductItem buyProductItem;
    }

    @Getter
    @Builder
    public static class Event {
        private final UserProductItem buyProductItem;
    }
}
