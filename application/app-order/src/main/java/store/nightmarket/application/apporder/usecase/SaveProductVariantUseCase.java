package store.nightmarket.application.apporder.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.out.SaveProductVariantPort;
import store.nightmarket.application.apporder.usecase.dto.SaveProductVariantUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.valueobject.Price;
import store.nightmarket.domain.order.valueobject.ProductVariantId;

@Service
@RequiredArgsConstructor
public class SaveProductVariantUseCase implements BaseUseCase<SaveProductVariantUseCaseDto.Input, Void> {

	private final SaveProductVariantPort saveProductVariantPort;

	@Override
	public Void execute(SaveProductVariantUseCaseDto.Input input) {
		ProductVariant productVariant = ProductVariant.newInstance(
			new ProductVariantId(input.productVariantId()),
			new Price(input.price())
		);

		saveProductVariantPort.save(productVariant);
		return null;
	}

}
