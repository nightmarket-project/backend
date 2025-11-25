package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;

import lombok.Builder;

public class ModifyShoppingBasketProductDto {

	@Builder
	public record Request(
		BigDecimal quantity
	) {

	}

}
