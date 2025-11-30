package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.PutShoppingBasketProductUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;

@Service
@RequiredArgsConstructor
public class PutShoppingBasketProductUseCase implements BaseUseCase<Input, Void> {

	private final SaveShoppingBasketProductPort saveShoppingBasketProductPort;

	@Override
	public Void execute(Input input) {
		saveShoppingBasketProductPort.save(
			ShoppingBasketProduct.newInstance(
				new ShoppingBasketProductId(UUID.randomUUID()),
				input.productVariantId(),
				input.userId(),
				input.name(),
				input.price(),
				input.quantity()
			)
		);
		return null;
	}

}
