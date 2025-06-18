package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.itemcore.model.Cart;

public class OrderItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private Inventory inventory;
        private Cart cart;
    }

    @Getter
    @Builder
    public static class Event {
        private Cart cart;
    }

}
