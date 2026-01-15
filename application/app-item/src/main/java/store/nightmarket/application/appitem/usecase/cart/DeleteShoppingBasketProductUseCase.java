package store.nightmarket.application.appitem.usecase.cart;

import static store.nightmarket.application.appitem.usecase.cart.dto.DeleteShoppingBasketProductUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.ShoppingBasketException;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;

@Service
@RequiredArgsConstructor
public class DeleteShoppingBasketProductUseCase implements BaseUseCase<Input, Void> {

	private final ReadShoppingBasketProductPort readShoppingBasketProductPort;
	private final DeleteShoppingBasketProductPort deleteShoppingBasketProductPort;

	@Override
	public Void execute(Input input) {
		ShoppingBasketProduct shoppingBasketProduct = readShoppingBasketProductPort.readOrThrow(
			input.shoppingBasketProductId());

		if (!shoppingBasketProduct.isOwner(input.userId())) {
			throw new ShoppingBasketException("Not Owner for ShoppingBasketProduct");
		}

		deleteShoppingBasketProductPort.delete(shoppingBasketProduct.getShoppingBasketProductId());
		return null;
	}

}
