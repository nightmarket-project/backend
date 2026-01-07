package store.nightmarket.application.appitem.usecase.cart.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public class ReadShoppingBasketUseCaseDto {

	@Builder
	public record Input(
		UserId userId
	) {

	}

	@Builder
	public record Output(
		List<ShoppingBasketProduct> shoppingBasketProductList,
		Map<ProductVariantId, ProductPostId> variantIdProductPostIdMap,
		List<ImageManager> imageManagerList
	) {

	}

}
