package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.UUID;

import lombok.Builder;

public class SaveShoppingBasketProductDto {

	@Builder
	public record Request(
		UUID productVariantId,
		String name,
		BigInteger price,
		BigInteger quantity
	) {

	}
}
