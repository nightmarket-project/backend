package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.CartProductId;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;

public class CartProduct extends BaseModel<CartProductId> {

    private ProductVariantId variantId;
    private Quantity quantity;
    private Price price;

    private CartProduct(
        CartProductId id,
        ProductVariantId variantId,
        Quantity quantity,
        Price price
    ) {
        super(id);
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }

    public CartProduct newInstance(
        CartProductId id,
        ProductVariantId variantId,
        Quantity quantity,
        Price price
    ) {
        return new CartProduct(
            id,
            variantId,
            quantity,
            price
        );
    }

}
