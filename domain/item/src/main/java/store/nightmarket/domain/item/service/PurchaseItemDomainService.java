package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.Cart;

public class PurchaseItemDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Cart cart = input.getCart();
        Inventory inventory = input.getInventory();

        inventory.purchase(cart);

        return Event.builder()
            .cart(cart)
            .build();
    }

}
