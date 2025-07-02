package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.dto.CancelPurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.CancelPurchaseItemDomainServiceDto.Input;

public class CancelPurchaseItemDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ProductVariant productVariant = input.getProductVariant();
        ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();

        if(productVariant.isNotSameAsProduct(shoppingBasketProduct)) {
            throw new ProductException("Product variant is not same as shopping basket product");
        }
        productVariant.restoreQuantity(shoppingBasketProduct);

        return Event.builder()
            .productVariant(productVariant)
            .build();
    }

}
