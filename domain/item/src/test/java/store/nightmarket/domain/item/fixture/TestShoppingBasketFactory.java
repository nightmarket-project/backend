package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.ShoppingBasketId;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class TestShoppingBasketFactory {

    public static ShoppingBasket createCart() {
        return ShoppingBasket.newInstance(
            new ShoppingBasketId(UUID.randomUUID()),
            new UserId(UUID.randomUUID()),
            new ArrayList<>()
        );
    }

    public static ShoppingBasketProduct createCartProduct(
        UUID cartProductId,
        UUID productVariantId,
        String name,
        int quantity,
        int price
    ) {
        return ShoppingBasketProduct.newInstance(
            new ShoppingBasketProductId(cartProductId),
            new ProductVariantId(productVariantId),
            new Name(name),
            new Quantity(BigDecimal.valueOf(quantity)),
            new Price(BigDecimal.valueOf(price))
        );
    }

}
