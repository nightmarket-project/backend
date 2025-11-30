package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ValidateProductStockUseCaseDto {

	@Builder
	public record Input(
		List<ProductQuantityDto> checkProductList
	) {

	}

	@Builder
	public record ProductQuantityDto(
		ProductVariantId productVariantId,
		Quantity quantity
	) {

	}

}
