package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

public class ModifyProductVariantDto {

	@Builder
	public record Request(
		String SKUCode,
		int quantity
	) {

	}

}
