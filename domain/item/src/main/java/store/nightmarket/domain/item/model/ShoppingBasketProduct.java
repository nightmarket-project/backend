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

@Getter
public class ShoppingBasketProduct extends BaseModel<ShoppingBasketProductId> {

    private final ProductVariantId variantId;
    private final Name name;
    private final Price unitPrice;
    private Quantity quantity;

    private ShoppingBasketProduct(
        ShoppingBasketProductId id,
        ProductVariantId variantId,
        Name name,
        Price unitPrice,
        Quantity quantity
    ) {
        super(id);
        this.variantId = variantId;
        this.name = name;
        this.unitPrice = unitPrice;

        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public static ShoppingBasketProduct newInstance(
        ShoppingBasketProductId id,
        ProductVariantId variantId,
        Name name,
        Price unitPrice,
        Quantity quantity
    ) {
        return new ShoppingBasketProduct(
            id,
            variantId,
            name,
            unitPrice,
            quantity
        );
    }

    private void validateQuantity(Quantity quantity) {
        if (quantity.isLessThan(new Quantity(BigDecimal.valueOf(1)))) {
            throw new ProductException("수량이 1보다 크거나 같아야 합니다");
        }
    }

    public void changeQuantity(Quantity quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public void increaseQuantity(Quantity quantity) {
        if (quantity.isLessThan(new Quantity(BigDecimal.ONE))) {
            throw new ProductException("0보다 작거나 같은 값은 수량 증가가 불가합니다.");
        }
        this.quantity = this.quantity.add(quantity);
    }

    public Price getTotalPrice() {
        return unitPrice.multiplyQuantity(quantity);
    }

    @Override
    public String toString() {
        return "CartProduct{" +
            "name=" + name.getValue() +
            '}';
    }

}
