package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class ValidateProductStockUseCaseDto {

	@Builder
	public record Input(
		List<ProductQuantityDto> checkProductList,
		UserId userId
	) {

	}

	@Builder
	public record ProductQuantityDto(
		ProductVariantId productVariantId,
		Quantity quantity
	) {

	}

}
