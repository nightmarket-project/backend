package store.nightmarket.domain.item.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;

public class PurchaseItemDomainService
	implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		ProductVariant productVariant = input.getProductVariant();
		ShoppingBasketProduct shoppingBasketProduct = input.getShoppingBasketProduct();

		if (productVariant.isNotSameAsProduct(shoppingBasketProduct.getProductVariantId())) {
			throw new ProductException("제품과 장바구니 상품이 일치하지 않습니다.");
		}
		productVariant.purchase(shoppingBasketProduct.getQuantity());

		return Event.builder()
			.productVariant(productVariant)
			.build();
	}

}
