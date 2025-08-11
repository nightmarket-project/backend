package store.nightmarket.domain.item.model;

import java.math.BigDecimal;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;

@Getter
public class ShoppingBasketProduct extends BaseModel<ShoppingBasketProductId> {

    private final ProductVariantId variantId;
    private final UserId userId;
    private final Name name;
    private final Price unitPrice;
    private Quantity quantity;

    private ShoppingBasketProduct(
        ShoppingBasketProductId id,
        ProductVariantId variantId,
        UserId userId,
        Name name,
        Price unitPrice,
        Quantity quantity
    ) {
        super(id);
        validateQuantity(quantity);
        this.variantId = variantId;
        this.userId = userId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public static ShoppingBasketProduct newInstance(
        ShoppingBasketProductId id,
        ProductVariantId variantId,
        UserId userId,
        Name name,
        Price unitPrice,
        Quantity quantity
    ) {
        return new ShoppingBasketProduct(
            id,
            variantId,
            userId,
            name,
            unitPrice,
            quantity
        );
    }

    public ShoppingBasketProductId getShoppingBasketProductId() {
        return internalId();
    }

    public Price getTotalPrice() {
        return unitPrice.multiplyQuantity(quantity);
    }

    public void changeQuantity(
        UserId userId,
        Quantity quantity
    ) {
        if (!this.userId.equals(userId)) {
            throw new ProductException("UserId is not the same");
        }
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(Quantity quantity) {
        if (quantity.isLessThan(new Quantity(BigDecimal.valueOf(1)))) {
            throw new ProductException("수량이 1보다 크거나 같아야 합니다");
        }
    }

    @Override
    public String toString() {
        return "CartProduct{" +
            "name=" + name.getValue() +
            '}';
    }

}
