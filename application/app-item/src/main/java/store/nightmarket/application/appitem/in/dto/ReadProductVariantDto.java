package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadProductVariantDto {

	@Builder
	public record Response(
		List<ProductVariantInfo> productVariantInfoList
	) {

	}

	@Builder
	public record ProductVariantInfo(
		UUID productVariantId,
		List<VariantOptionValueInfo> variantOptionValueInfoList
	) {

	}

	@Builder
	public record VariantOptionValueInfo(
		UUID optionGroupId,
		UUID optionValueId
	) {

	}
}
