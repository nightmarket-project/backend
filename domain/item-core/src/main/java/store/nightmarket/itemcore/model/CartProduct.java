package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.CartProductId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;

@Getter
public class CartProduct extends BaseModel<CartProductId> {

    private ProductVariantId variantId;
    private Name name;
    private Quantity quantity;
    private Price price;

    private CartProduct(
        CartProductId id,
        ProductVariantId variantId,
        Name name,
        Quantity quantity,
        Price price
    ) {
        super(id);
        this.variantId = variantId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static CartProduct newInstance(
        CartProductId id,
        ProductVariantId variantId,
        Name name,
        Quantity quantity,
        Price price
    ) {
        return new CartProduct(
            id,
            variantId,
            name,
            quantity,
            price
        );
    }

    public void changeQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
            "name=" + name.getValue() +
            '}';
    }

}
