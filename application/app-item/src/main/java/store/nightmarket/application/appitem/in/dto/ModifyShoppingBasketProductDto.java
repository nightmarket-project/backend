package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

public class ModifyShoppingBasketProductDto {

	@Builder
	public record Request(
		UUID userId,
		BigDecimal quantity
	) {

	}

}
