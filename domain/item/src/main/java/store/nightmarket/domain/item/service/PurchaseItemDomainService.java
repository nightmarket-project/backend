package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;
import store.nightmarket.domain.item.model.ShoppingBasket;

public class PurchaseItemDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ShoppingBasket shoppingBasket = input.getShoppingBasket();
        Inventory inventory = input.getInventory();

        inventory.purchase(shoppingBasket);

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
