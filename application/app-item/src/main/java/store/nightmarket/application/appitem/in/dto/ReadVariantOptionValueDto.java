package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadVariantOptionValueDto {

	@Builder
	public record Response(
		List<VariantOptionValueInfo> variantOptionValueList
	) {

	}

	@Builder
	public record VariantOptionValueInfo(
		UUID optionGroupId,
		UUID optionValueId
	) {

	}

}
