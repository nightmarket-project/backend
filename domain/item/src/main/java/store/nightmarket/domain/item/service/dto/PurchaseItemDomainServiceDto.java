package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.ShoppingBasket;

public class PurchaseItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private final Inventory inventory;
        private final ShoppingBasket shoppingBasket;
    }

    @Getter
    @Builder
    public static class Event {
        private final ShoppingBasket shoppingBasket;
    }

}
