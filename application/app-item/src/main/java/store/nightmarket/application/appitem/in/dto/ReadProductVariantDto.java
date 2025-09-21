package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadProductVariantDto {

	@Builder
	public record Response(
		List<ProductVariantInfo> productVariantList
	) {

	}

	@Builder
	public record ProductVariantInfo(
		UUID productVariantId,
		List<VariantOptionValueInfo> variantOptionValue
	) {

	}

	@Builder
	public record VariantOptionValueInfo(
		UUID optionGroupId,
		UUID optionValueId
	) {

	}
}
