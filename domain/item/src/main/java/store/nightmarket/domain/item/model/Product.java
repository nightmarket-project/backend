package store.nightmarket.domain.item.model;

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

	public ProductId getProductId() {
		return internalId();
	}

}
