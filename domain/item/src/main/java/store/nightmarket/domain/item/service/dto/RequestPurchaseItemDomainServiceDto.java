package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductItem;

public class RequestPurchaseItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final ProductItem productItem;
        private final ProductItem buyProductItem;
    }

    @Getter
    @Builder
    public static class Event {
        private final ProductItem productItem;
    }
}
