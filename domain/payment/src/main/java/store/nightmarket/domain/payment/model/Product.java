package store.nightmarket.domain.payment.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.payment.valueobject.Price;
import store.nightmarket.domain.payment.valueobject.ProductId;

@Getter
public class Product extends BaseModel<ProductId> {

	private Price price;

	private Product(
		ProductId id,
		Price price
	) {
		super(id);
		this.price = price;
	}

	public static Product newInstance(
		ProductId productId,
		Price price
	) {
		return new Product(
			productId,
			price
		);
	}

}
