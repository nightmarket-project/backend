package store.nightmarket.application.appitem.out.cart;

import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;

public interface DeleteShoppingBasketProductPort {

	void delete(ShoppingBasketProductId shoppingBasketProductId);

}
