package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

public class ReadProductVariantResponseDto {

	@Builder
	public record Response(
		List<ProductVariantControllerDto> productVariantControllerDtoList
	) {

	}

	@Builder
	public record ProductVariantControllerDto(
		ProductVariantId productVariantId,
		List<VariantOptionValueControllerDto> variantOptionValueControllerDtoList
	) {

	}

	@Builder
	public record VariantOptionValueControllerDto(
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {

	}
}
