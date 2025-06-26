package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.ShoppingBasket;

public class OrderItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {
        private Inventory inventory;
        private ShoppingBasket shoppingBasket;
    }

    @Getter
    @Builder
    public static class Event {
        private ShoppingBasket shoppingBasket;
    }

}
