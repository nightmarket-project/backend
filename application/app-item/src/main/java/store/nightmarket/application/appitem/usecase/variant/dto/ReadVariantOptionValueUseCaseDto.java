package store.nightmarket.application.appitem.usecase.variant.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.ProductVariantId;

public class ReadVariantOptionValueUseCaseDto {

	@Builder
	public record Input(
		ProductVariantId productVariantId
	) {

	}

	@Builder
	public record Output(
		List<VariantOptionValue> variantOptionValueList
	) {

	}

}
