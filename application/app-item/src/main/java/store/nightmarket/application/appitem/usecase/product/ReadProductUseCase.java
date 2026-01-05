package store.nightmarket.application.appitem.usecase.product;

import static store.nightmarket.application.appitem.usecase.product.dto.ReadProductUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Product;

@Service
@RequiredArgsConstructor
public class ReadProductUseCase implements BaseUseCase<Input, Output> {

	private final ReadProductPort readProductPort;

	@Override
	public Output execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		return Output.builder()
			.product(product)
			.build();
	}

}
