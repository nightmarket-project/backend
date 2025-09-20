package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadShoppingBasketUseCase implements BaseUseCase<Input, Output> {

	private final ReadShoppingBasketProductPort readShoppingBasketProductPort;

	@Override
	public Output execute(Input input) {
		return Output.builder()
			.shoppingBasketProductList(readShoppingBasketProductPort.readListByUserId(input.userId()))
			.build();
	}

}
