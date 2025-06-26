package store.nightmarket.itemcore.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.itemcore.exception.InventoryException;
import store.nightmarket.itemcore.model.Inventory;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.service.dto.OrderItemDomainServiceDto.Event;
import store.nightmarket.itemcore.service.dto.OrderItemDomainServiceDto.Input;

public class OrderItemDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Inventory inventory = input.getInventory();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

         String errorMessages = inventory.tryOrdering(shoppingBasket);
         if(!errorMessages.isEmpty()) {
             throw new InventoryException(errorMessages);
         }

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}

