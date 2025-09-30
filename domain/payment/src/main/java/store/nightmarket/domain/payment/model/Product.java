package store.nightmarket.domain.payment.model;

import java.time.LocalDateTime;

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

	private Product(
		ProductId id,
		LocalDateTime createdAt,
		Price price
	) {
		super(id, createdAt);
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

	public static Product newInstanceWithCreatedAt(
		ProductId productId,
		LocalDateTime createdAt,
		Price price
	) {
		return new Product(
			productId,
			createdAt,
			price
		);
	}

	public ProductId getProductId() {
		return internalId();
	}

}
