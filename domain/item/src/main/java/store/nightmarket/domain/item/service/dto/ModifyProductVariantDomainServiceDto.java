package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ModifyProductVariantDomainServiceDto {

	@Builder
	public record Input(
		ProductVariant productVariant,
		String SKUCode,
		Quantity quantity
	) {

	}

	@Builder
	public record Event(
		ProductVariant productVariant
	) {

	}

}
