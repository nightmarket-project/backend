package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.RegisterProductVariantUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductVariantPort;
import store.nightmarket.application.appitem.out.SaveVariantOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;

@Service
@RequiredArgsConstructor
public class RegisterProductVariantUseCase implements BaseUseCase<Input, Void> {

	private final SaveProductVariantPort saveProductVariantPort;
	private final SaveVariantOptionValuePort saveVariantOptionValuePort;

	@Override
	public Void execute(Input input) {
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());

		saveProductVariantPort.save(
			ProductVariant.newInstance(
				productVariantId,
				input.productId(),
				input.userId(),
				input.SKUCode(),
				input.quantity()
			)
		);

		saveVariantOptionValuePort.saveAll(
			getVariantOptionValueList(
				input,
				productVariantId
			)
		);

		return null;
	}

	private static List<VariantOptionValue> getVariantOptionValueList(Input input, ProductVariantId productVariantId) {
		return input.optionCombinationList().stream()
			.map(optionCombination ->
				VariantOptionValue.newInstance(
					new VariantOptionValueId(UUID.randomUUID()),
					productVariantId,
					optionCombination.optionGroupId(),
					optionCombination.optionValueId()
				)
			)
			.toList();
	}
}
