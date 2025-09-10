package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

public class ReadProductVariantDto {

	@Builder
	public record Response(
		List<ProductVariantInfo> productVariantInfoList
	) {

	}

	@Builder
	public record ProductVariantInfo(
		ProductVariantId productVariantId,
		List<VariantOptionValueInfo> variantOptionValueInfoList
	) {

	}

	@Builder
	public record VariantOptionValueInfo(
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {

	}
}
