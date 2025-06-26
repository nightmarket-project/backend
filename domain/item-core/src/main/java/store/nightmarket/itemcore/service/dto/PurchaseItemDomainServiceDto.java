package store.nightmarket.itemcore.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.Inventory;
import store.nightmarket.itemcore.model.ShoppingBasket;

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
