package store.nightmarket.domain.item.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.service.dto.PurchaseManyProductItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseManyProductItemDomainServiceDto.Input;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

public class PurchaseManyProductItemDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        List<ProductVariant> purchaseProductList = input.getPurchaseProductList();
        ShoppingBasket shoppingBasket = input.getShoppingBasket();

        if(purchaseProductList.isEmpty()) {
            throw new ProductException("No product found");
        }
        Map<ProductVariantId, ProductVariant> purchaseProductMap = purchaseProductList.stream()
            .collect(Collectors.toMap(ProductVariant::getProductVariantId, Function.identity()));

        shoppingBasket.getShoppingBasket()
            .forEach(shoppingBasketProduct -> {
                ProductVariantId productVariantId = shoppingBasketProduct.getVariantId();
                if (!purchaseProductMap.containsKey(productVariantId)) {
                    throw new ProductException(
                        "Product variant is not same as shopping basket product: "
                            + shoppingBasketProduct.getName());
                }

                ProductVariant purchaseProduct = purchaseProductMap.get(productVariantId);
                if (purchaseProduct.isNotAbleToPurchase(shoppingBasketProduct)) {
                    throw new QuantityException("Product is not available to purchase: "
                        + shoppingBasketProduct.getName());
                }
                purchaseProduct.purchase(shoppingBasketProduct);
            });

        return Event.builder()
            .shoppingBasket(shoppingBasket)
            .build();
    }

}
