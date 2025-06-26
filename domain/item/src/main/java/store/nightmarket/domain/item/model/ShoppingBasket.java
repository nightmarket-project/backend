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
    private List<ShoppingBasketProduct> shoppingBasket;

    private ShoppingBasket(
        CartId id,
        UserId userId,
        List<ShoppingBasketProduct> shoppingBasket
    ) {
        super(id);
        this.userId = userId;
        this.shoppingBasket = shoppingBasket;
    }

    public static ShoppingBasket newInstance(
        CartId id,
        UserId userId,
        List<ShoppingBasketProduct> shoppingBasket
    ) {
        return new ShoppingBasket(
            id,
            userId,
            shoppingBasket
        );
    }

    public void add(ShoppingBasketProduct shoppingBasketProduct) {
        shoppingBasket.add(shoppingBasketProduct);
    }

    public void remove(ShoppingBasketProduct shoppingBasketProduct) {
        if(!shoppingBasket.contains(shoppingBasketProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + shoppingBasketProduct.toString());
        }
        shoppingBasket.remove(shoppingBasketProduct);
    }

    public void changeProductQuantity(ShoppingBasketProduct shoppingBasketProduct, Quantity quantity) {
        if(!shoppingBasket.contains(shoppingBasketProduct)) {
            throw new ItemCoreException("상품이 존재하지 않습니다: " + shoppingBasketProduct.toString());
        }

        if(quantity.isLessThan(new Quantity(BigDecimal.valueOf(1)))) {
            throw new ItemCoreException("상품을 수량이 1보다 작게 변경할 수 없습니다." + shoppingBasketProduct.toString());
        }

        shoppingBasket.stream()
            .filter(product -> product.getVariantId().equals(shoppingBasketProduct.getVariantId()))
            .findFirst()
            .ifPresent(product->product.changeQuantity(quantity));
    }

}
