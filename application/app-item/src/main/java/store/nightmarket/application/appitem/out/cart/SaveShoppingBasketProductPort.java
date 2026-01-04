package store.nightmarket.application.appitem.out.cart;

import store.nightmarket.domain.item.model.ShoppingBasketProduct;

public interface SaveShoppingBasketProductPort {

	void save(ShoppingBasketProduct shoppingBasketProduct);

}
