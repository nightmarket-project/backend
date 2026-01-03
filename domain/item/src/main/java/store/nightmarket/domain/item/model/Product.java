package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

@Getter
public class Product extends BaseModel<ProductId> {

	private final UserId userId;
	private Name name;
	private String description;
	private Price price;

	private Product(
		ProductId id,
		UserId userId,
		Name name,
		String description,
		Price price
	) {
		super(id);
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	private Product(
		ProductId id,
		LocalDateTime createdAt,
		UserId userId,
		Name name,
		String description,
		Price price
	) {
		super(id, createdAt);
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public static Product newInstance(
		ProductId id,
		UserId userId,
		Name name,
		String description,
		Price price
	) {
		return new Product(
			id,
			userId,
			name,
			description,
			price
		);
	}

	public static Product newInstanceWithCreatedAt(
		ProductId id,
		LocalDateTime createdAt,
		UserId userId,
		Name name,
		String description,
		Price price
	) {
		return new Product(
			id,
			createdAt,
			userId,
			name,
			description,
			price
		);
	}

	public ProductId getProductId() {
		return internalId();
	}

	public boolean isOwner(UserId userId) {
		return this.userId.equals(userId);
	}

}
