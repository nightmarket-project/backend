package store.nightmarket.domain.item.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.service.dto.CancelManyPurchaseProductItemDomainServiceDto.Input;
import store.nightmarket.domain.item.service.dto.CancelManyPurchaseProductItemDomainServiceDto.Event;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class CancelManyPurchaseProductItemDomainServiceDto
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        List<ProductVariant> cancelProductList = input.getProductVariantList();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        if(cancelProductList.isEmpty()) {
            throw new ProductException("No product found");
        }
        Map<ProductVariantId, ProductVariant> cancelProductMap = cancelProductList.stream()
            .collect(Collectors.toMap(ProductVariant::getProductVariantId, Function.identity()));

        shoppingBasket.getShoppingBasket()
            .forEach(shoppingBasketProduct -> {
                ProductVariantId productVariantId = shoppingBasketProduct.getVariantId();
                if (!cancelProductMap.containsKey(productVariantId)) {
                    throw new ProductException(
                        "Product variant is not same as shopping basket product: "
                            + shoppingBasketProduct.getName());
                }

                ProductVariant productVariant = cancelProductMap.get(productVariantId);
                productVariant.restoreQuantity(shoppingBasketProduct);
            });

        return Event.builder()
            .productVariantList(cancelProductList)
            .build();
    }

}
