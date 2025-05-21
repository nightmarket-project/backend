package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.valueobject.ShoppingBasketId;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket extends BaseModel<ShoppingBasketId> {

    private UserId userId;
    @Getter
    private List<UserBuyProductItem> userBuyProductItems;

    private ShoppingBasket(
            ShoppingBasketId id,
            UserId userId
    ) {
        super(id);
        this.userId = userId;
        userBuyProductItems = new ArrayList<>();
    }

    public static ShoppingBasket newInstance(
            ShoppingBasketId id,
            UserId userId
    ) {
        return new ShoppingBasket(
                id,
                userId
        );
    }

    public void addProductToBasket(UserBuyProductItem item) {
        userBuyProductItems.add(item);
    }

    public void removeProductFromBasket(UserBuyProductItem item) {
        userBuyProductItems.remove(item);
    }

    public boolean hasProductInBasket(UserBuyProductItem item) {
        return userBuyProductItems.contains(item);
    }

}
