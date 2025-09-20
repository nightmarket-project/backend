package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.UserId;

public class ReadShoppingBasketUseCaseDto {

	@Builder
	public record Input(
		UserId userId
	) {

	}

	@Builder
	public record Output(
		List<ShoppingBasketProduct> shoppingBasketProductList
	) {

	}

}
