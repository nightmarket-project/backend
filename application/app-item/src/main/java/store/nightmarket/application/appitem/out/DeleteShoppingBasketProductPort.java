package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;

public interface DeleteShoppingBasketProductPort {

	void delete(ShoppingBasketProductId shoppingBasketProductId);

}
