package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.util.UUID;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class TestShoppingBasketFactory {

    public static ShoppingBasketProduct createCartProduct(
        UUID shoppingBasketProductId,
        UUID productVariantId,
        UUID userId,
        String name,
        int price,
        int quantity
    ) {
        return ShoppingBasketProduct.newInstance(
            new ShoppingBasketProductId(shoppingBasketProductId),
            new ProductVariantId(productVariantId),
            new UserId(userId),
            new Name(name),
            new Price(BigDecimal.valueOf(price)),
            new Quantity(BigDecimal.valueOf(quantity))
        );
    }

}
