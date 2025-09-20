package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class DeleteShoppingBasketProductUseCase implements BaseUseCase<Input, Void> {

	private final DeleteShoppingBasketProductPort deleteShoppingBasketProductPort;

	@Override
	public Void execute(Input input) {
		deleteShoppingBasketProductPort.delete(input.shoppingBasketProductId());
		return null;
	}

}
