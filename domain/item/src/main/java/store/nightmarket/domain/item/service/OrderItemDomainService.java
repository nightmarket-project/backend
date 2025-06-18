package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.InventoryException;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.Cart;

public class OrderItemDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Inventory inventory = input.getInventory();
        Cart cart = input.getCart();

         String errorMessages = inventory.getErrorMessages(cart);
         if(!errorMessages.isEmpty()) {
             throw new InventoryException(errorMessages);
         }

        return Event.builder()
            .cart(cart)
            .build();
    }

}

