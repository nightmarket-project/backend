package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
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
		UUID productVariantId,
		UUID productPostId,
		String name,
		BigDecimal quantity,
		BigDecimal price,
		String imageUrl
	) {

	}

}
