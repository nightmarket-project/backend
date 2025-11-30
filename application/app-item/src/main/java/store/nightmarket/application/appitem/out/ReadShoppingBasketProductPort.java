package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Optional;

import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;

public interface ReadShoppingBasketProductPort {

	Optional<ShoppingBasketProduct> read(ShoppingBasketProductId shoppingBasketProductId);

	default ShoppingBasketProduct readOrThrow(ShoppingBasketProductId shoppingBasketProductId) {
		return read(shoppingBasketProductId)
			.orElseThrow(() -> new ProductException("Shopping basket product not found"));
	}

	List<ShoppingBasketProduct> readListByUserId(UserId userId);

}
