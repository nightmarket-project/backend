package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.CartProductId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

@Getter
public class ShoppingBaseketProduct extends BaseModel<CartProductId> {

    private ProductVariantId variantId;
    private Name name;
    private Quantity quantity;
    private Price price;

    private ShoppingBaseketProduct(
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

    public static ShoppingBaseketProduct newInstance(
        CartProductId id,
        ProductVariantId variantId,
        Name name,
        Quantity quantity,
        Price price
    ) {
        return new ShoppingBaseketProduct(
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
