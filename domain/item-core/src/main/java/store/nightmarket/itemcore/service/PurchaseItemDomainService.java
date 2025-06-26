package store.nightmarket.itemcore.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.model.Inventory;
import store.nightmarket.itemcore.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.itemcore.service.dto.PurchaseItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.ShoppingBasket;

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
