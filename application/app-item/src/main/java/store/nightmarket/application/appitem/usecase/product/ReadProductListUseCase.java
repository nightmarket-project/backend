package store.nightmarket.application.appitem.usecase.product;

import static store.nightmarket.application.appitem.usecase.product.dto.ReadProductListUseCaseDto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Product;

@Service
@RequiredArgsConstructor
public class ReadProductListUseCase implements BaseUseCase<Input, Output> {

	private final ReadProductPort readProductPort;

	@Override
	public Output execute(Input input) {
		Pageable pageable = PageRequest.of(input.page(), input.size());
		Page<Product> productPage = readProductPort.readAll(pageable);

		return Output.builder()
			.productPage(productPage)
			.build();
	}

}
