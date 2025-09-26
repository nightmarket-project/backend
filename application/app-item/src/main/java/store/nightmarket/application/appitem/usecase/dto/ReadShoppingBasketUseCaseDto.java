package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ProductPostId;

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
