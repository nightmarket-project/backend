package store.nightmarket.itemcore.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.valueobject.CartId;
import store.nightmarket.itemcore.valueobject.UserId;

@Getter
public class Cart extends BaseModel<CartId> {

    private UserId userId;
    private List<CartProduct> shoppingBasket;

    private Cart(
        CartId id,
        UserId userId
    ) {
        super(id);
        this.userId = userId;
        shoppingBasket = new ArrayList<>();
    }

    public static Cart newInstance(
        CartId id,
        UserId userId
    ) {
        return new Cart(
            id,
            userId
        );
    }

    public void add(CartProduct cartProduct) {
        shoppingBasket.add(cartProduct);
    }

    public void remove(CartProduct cartProduct) {
        if(!shoppingBasket.contains(cartProduct)) {
            throw new ItemCoreException("Can't remove cart product " + cartProduct.toString());
        }
        shoppingBasket.remove(cartProduct);
    }

}
