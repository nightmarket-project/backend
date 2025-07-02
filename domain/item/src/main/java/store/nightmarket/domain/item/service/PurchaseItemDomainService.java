package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;

public class PurchaseItemDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ProductVariant productVariant = input.getProductVariant();
        ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();

        if(productVariant.isNotSameAsProduct(shoppingBasketProduct)) {
            throw new ProductException("Product variant is not same as shopping basket product");
        }
        if (productVariant.isNotAbleToPurchase(shoppingBasketProduct)) {
            throw new QuantityException("Product is not available to purchase");
        }
        productVariant.purchase(shoppingBasketProduct);

        return Event.builder()
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();
    }

}
