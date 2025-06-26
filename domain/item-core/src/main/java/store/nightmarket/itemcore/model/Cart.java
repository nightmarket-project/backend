package store.nightmarket.itemcore.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.valueobject.CartId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;

@Getter
public class Cart extends BaseModel<CartId> {

    private UserId userId;
    private List<CartProduct> shoppingBasket;

    private Cart(
        CartId id,
        UserId userId,
        List<CartProduct> shoppingBasket
    ) {
        super(id);
        this.userId = userId;
        this.shoppingBasket = shoppingBasket;
    }

    public static Cart newInstance(
        CartId id,
        UserId userId,
        List<CartProduct> shoppingBasket
    ) {
        return new Cart(
            id,
            userId,
            shoppingBasket
        );
    }

    public void add(CartProduct cartProduct) {
        shoppingBasket.add(cartProduct);
    }

    public void remove(CartProduct cartProduct) {
        if(!shoppingBasket.contains(cartProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + cartProduct.toString());
        }
        shoppingBasket.remove(cartProduct);
    }

    public void changeProductQuantity(CartProduct cartProduct, Quantity quantity) {
        if(!shoppingBasket.contains(cartProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + cartProduct.toString());
        }

        if(quantity.isLessThan(new Quantity(BigDecimal.valueOf(1)))) {
            throw new ItemCoreException("상품을 수량이 1보다 작게 변경할 수 없습니다." + cartProduct.toString());
        }

        shoppingBasket.stream()
            .filter(product -> product.getVariantId().equals(cartProduct.getVariantId()))
            .findFirst()
            .ifPresent(product->product.changeQuantity(quantity));
    }

}
