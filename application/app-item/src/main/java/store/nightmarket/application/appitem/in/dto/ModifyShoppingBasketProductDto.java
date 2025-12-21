package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;

import lombok.Builder;

public class ModifyShoppingBasketProductDto {

	@Builder
	public record Request(
		BigInteger quantity
	) {

	}

}
