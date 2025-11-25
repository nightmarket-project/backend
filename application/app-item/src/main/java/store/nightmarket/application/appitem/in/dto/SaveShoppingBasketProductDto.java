package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

public class SaveShoppingBasketProductDto {

	@Builder
	public record Request(
		UUID productVariantId,
		String name,
		BigDecimal price,
		BigDecimal quantity
	) {

	}
}
