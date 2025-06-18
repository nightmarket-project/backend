package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.util.UUID;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemcore.valueobject.CartId;
import store.nightmarket.itemcore.valueobject.CartProductId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;

public class CartFactory {

    public static Cart createCart() {
        return Cart.newInstance(
            new CartId(UUID.randomUUID()),
            new UserId(UUID.randomUUID())
        );
    }

    public static CartProduct createCartProduct(
        UUID productVariantId,
        String name,
        int quantity,
        int price
    ) {
        return CartProduct.newInstance(
            new CartProductId(UUID.randomUUID()),
            new ProductVariantId(productVariantId),
            new Name(name),
            new Quantity(BigDecimal.valueOf(quantity)),
            new Price(BigDecimal.valueOf(price))
        );
    }

}
