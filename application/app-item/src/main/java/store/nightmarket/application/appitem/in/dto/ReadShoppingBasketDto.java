package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadShoppingBasketDto {

	@Builder
	public record Response(
		List<ShoppingBasketProductInfo> shoppingBasket
	) {

	}

	@Builder
	public record ShoppingBasketProductInfo(
		UUID shoppingBasketId,
		UUID productVariantId,
		UUID productPostId,
		String name,
		BigInteger quantity,
		BigInteger price,
		String imageUrl
	) {

	}

}
