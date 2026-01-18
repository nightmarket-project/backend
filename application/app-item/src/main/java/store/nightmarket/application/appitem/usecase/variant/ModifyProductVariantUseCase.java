package store.nightmarket.application.appitem.usecase.variant;

import static store.nightmarket.application.appitem.usecase.variant.dto.ModifyProductVariantUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.SaveProductVariantPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.ProductVariantException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.service.ModifyProductVariantDomainService;
import store.nightmarket.domain.item.service.dto.ModifyProductVariantDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyProductVariantUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final ReadProductVariantPort readProductVariantPort;
	private final SaveProductVariantPort saveProductVariantPort;
	private final ModifyProductVariantDomainService modifyProductVariantDomainService;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductVariantException("Not Owner For ProductVariant");
		}

		ProductVariant productVariant = readProductVariantPort.readOrThrow(input.productVariantId());

		ModifyProductVariantDomainServiceDto.Event event = modifyProductVariantDomainService.execute(
			ModifyProductVariantDomainServiceDto.Input.builder()
				.productVariant(productVariant)
				.SKUCode(input.SKUCode())
				.quantity(input.quantity())
				.build()
		);

		saveProductVariantPort.save(event.productVariant());
		return null;
	}

}
