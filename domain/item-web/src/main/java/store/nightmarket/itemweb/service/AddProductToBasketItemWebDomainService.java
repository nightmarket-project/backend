package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.domain.item.model.UserBuyItemGroup;
import store.nightmarket.itemcore.model.Item;
import store.nightmarket.itemcore.model.UserItem;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemcore.model.Cart;

import java.util.List;

import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Input;

public class AddProductToBasketItemWebDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Item item = input.getItem();
        UserItem userBuyItem = input.getUserBuyItem();
        Cart cart = input.getCart();

        if (!item.getItemId().equals(userBuyItem.getItemId())) {
            throw new ItemWebException("Product item id does not match buy product item id");
        }

        List<ItemOptionValidationError> productItemErrors = itemGroup.findProductItemErrors(
            userBuyItemGroup);
        if (!productItemErrors.isEmpty()) {
            throw new ItemWebException("Product item does not exist");
        }
        cart.addProductToBasket(userBuyItemGroup);


        return Event.builder()
                .cart(cart)
                .build();
    }

}
