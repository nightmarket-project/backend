package store.nightmarket.application.appitem.usecase.product;

import static store.nightmarket.application.appitem.usecase.product.dto.ModifyProductUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.service.ModifyProductDomainService;
import store.nightmarket.domain.item.service.dto.ModifyProductDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyProductUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final SaveProductPort saveProductPort;
	private final ModifyProductDomainService modifyProductDomainService;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductException("Not Owner For Product");
		}

		ModifyProductDomainServiceDto.Event event = modifyProductDomainService.execute(
			ModifyProductDomainServiceDto.Input.builder()
				.product(product)
				.name(input.name())
				.description(input.description())
				.price(input.price())
				.build()
		);

		saveProductPort.save(event.product());
		return null;
	}

}
