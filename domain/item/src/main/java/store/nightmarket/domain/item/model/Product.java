package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;

@Getter
public class Product extends BaseModel<ProductId> {

	private Name name;
	private String description;
	private Price price;

	private Product(
		ProductId id,
		Name name,
		String description,
		Price price
	) {
		super(id);
		this.name = name;
		this.description = description;
		this.price = price;
	}

	private Product(
		ProductId id,
		LocalDateTime createdAt,
		Name name,
		String description,
		Price price
	) {
		super(id, createdAt);
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public static Product newInstance(
		ProductId id,
		Name name,
		String description,
		Price price
	) {
		return new Product(
			id,
			name,
			description,
			price
		);
	}

	public static Product newInstanceWithCreatedAt(
		ProductId id,
		LocalDateTime createdAt,
		Name name,
		String description,
		Price price
	) {
		return new Product(
			id,
			createdAt,
			name,
			description,
			price
		);
	}

	public ProductId getProductId() {
		return internalId();
	}

}
