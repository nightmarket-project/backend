package store.nightmarket.itemweb.fixture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.CartId;
import store.nightmarket.domain.item.valueobject.CartProductId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class TestShoppingBasketFactory {

    public static ShoppingBasket createCart() {
        return ShoppingBasket.newInstance(
            new CartId(UUID.randomUUID()),
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
            new CartProductId(cartProductId),
            new ProductVariantId(productVariantId),
            new Name(name),
            new Quantity(BigDecimal.valueOf(quantity)),
            new Price(BigDecimal.valueOf(price))
        );
    }

}
