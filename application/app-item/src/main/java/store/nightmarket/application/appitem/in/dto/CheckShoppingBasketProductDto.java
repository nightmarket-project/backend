package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class CheckShoppingBasketProductDto {

	@Builder
	public record Request(
		List<ProductQuantityInfo> checkProduct
	) {

	}

	@Builder
	public record ProductQuantityInfo(
		UUID productVariantId,
		int quantity
	) {

	}

}
