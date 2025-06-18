package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.itemcore.model.Cart;

public class PurchaseItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final Inventory inventory;
        private final Cart cart;

    }

    @Getter
    @Builder
    public static class Event {

        private final Cart cart;

    }

}
