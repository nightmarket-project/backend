package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.model.ShoppingBasket;

import java.util.List;

import static store.nightmarket.itemweb.service.dto.addProductToBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.addProductToBasketItemWebDomainServiceDto.Input;

public class addProductToBasketItemWebDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ProductItem productItem = input.getProductItem();
        UserBuyProductItem userBuyProductItem = input.getUserBuyProductItem();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        if (!productItem.getItemId().equals(userBuyProductItem.getItemId())) {
            throw new ItemWebException("Product item id does not match buy product item id");
        }

        List<ItemOptionValidationError> productItemErrors = productItem.findProductItemErrors(userBuyProductItem);
        if (!productItemErrors.isEmpty()) {
            throw new ItemWebException("Product item does not exist");
        }
        shoppingBasket.addProductToBasket(userBuyProductItem);


        return Event.builder()
                .shoppingBasket(shoppingBasket)
                .build();
    }

}
