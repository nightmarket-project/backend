package store.nightmarket.itemcore.fixture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.valueobject.CartId;
import store.nightmarket.itemcore.valueobject.CartProductId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;

public class TestShoppingBasketFactory {

    public static ShoppingBasket createCart() {
        return ShoppingBasket.newInstance(
            new CartId(UUID.randomUUID()),
            new UserId(UUID.randomUUID()),
            new ArrayList<>()
        );
    }

    public static ShoppingBaseketProduct createCartProduct(
        UUID cartProductId,
        UUID productVariantId,
        String name,
        int quantity,
        int price
    ) {
        return ShoppingBaseketProduct.newInstance(
            new CartProductId(cartProductId),
            new ProductVariantId(productVariantId),
            new Name(name),
            new Quantity(BigDecimal.valueOf(quantity)),
            new Price(BigDecimal.valueOf(price))
        );
    }

}
