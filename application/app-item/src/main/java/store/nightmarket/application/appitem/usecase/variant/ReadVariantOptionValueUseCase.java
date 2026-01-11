package store.nightmarket.application.appitem.usecase.variant;

import static store.nightmarket.application.appitem.usecase.variant.dto.ReadVariantOptionValueUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.variant.ReadVariantOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.VariantOptionValue;

@Service
@RequiredArgsConstructor
public class ReadVariantOptionValueUseCase implements BaseUseCase<Input, Output> {

	private final ReadVariantOptionValuePort readVariantOptionValuePort;

	@Override
	public Output execute(Input input) {
		List<VariantOptionValue> variantOptionValueList
			= readVariantOptionValuePort.readByIdProductVariantId(input.productVariantId());

		return Output.builder()
			.variantOptionValueList(variantOptionValueList)
			.build();
	}

}
