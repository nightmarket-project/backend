package store.nightmarket.domain.item.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.ItemCoreException;
import store.nightmarket.domain.item.valueobject.CartId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

@Getter
public class ShoppingBasket extends BaseModel<CartId> {

    private UserId userId;
    private List<ShoppingBaseketProduct> shoppingBasket;

    private ShoppingBasket(
        CartId id,
        UserId userId,
        List<ShoppingBaseketProduct> shoppingBasket
    ) {
        super(id);
        this.userId = userId;
        this.shoppingBasket = shoppingBasket;
    }

    public static ShoppingBasket newInstance(
        CartId id,
        UserId userId,
        List<ShoppingBaseketProduct> shoppingBasket
    ) {
        return new ShoppingBasket(
            id,
            userId,
            shoppingBasket
        );
    }

    public void add(ShoppingBaseketProduct shoppingBaseketProduct) {
        shoppingBasket.add(shoppingBaseketProduct);
    }

    public void remove(ShoppingBaseketProduct shoppingBaseketProduct) {
        if(!shoppingBasket.contains(shoppingBaseketProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + shoppingBaseketProduct.toString());
        }
        shoppingBasket.remove(shoppingBaseketProduct);
    }

    public void changeProductQuantity(ShoppingBaseketProduct shoppingBaseketProduct, Quantity quantity) {
        if(!shoppingBasket.contains(shoppingBaseketProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + shoppingBaseketProduct.toString());
        }

        if(quantity.isLessThan(new Quantity(BigDecimal.valueOf(1)))) {
            throw new ItemCoreException("상품을 수량이 1보다 작게 변경할 수 없습니다." + shoppingBaseketProduct.toString());
        }

        shoppingBasket.stream()
            .filter(product -> product.getVariantId().equals(shoppingBaseketProduct.getVariantId()))
            .findFirst()
            .ifPresent(product->product.changeQuantity(quantity));
    }

}
