package store.nightmarket.domain.item.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

@Getter
public class ShoppingBasketProduct extends BaseModel<ShoppingBasketProductId> {

	private final ProductVariantId productVariantId;
	private final UserId userId;
	private final Name name;
	private final Price unitPrice;
	private Quantity quantity;

	private ShoppingBasketProduct(
		ShoppingBasketProductId id,
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price unitPrice,
		Quantity quantity
	) {
		super(id);
		validateQuantity(quantity);
		this.productVariantId = productVariantId;
		this.userId = userId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	private ShoppingBasketProduct(
		ShoppingBasketProductId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price unitPrice,
		Quantity quantity
	) {
		super(id, createdAt);
		validateQuantity(quantity);
		this.productVariantId = productVariantId;
		this.userId = userId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public static ShoppingBasketProduct newInstance(
		ShoppingBasketProductId id,
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price unitPrice,
		Quantity quantity
	) {
		return new ShoppingBasketProduct(
			id,
			productVariantId,
			userId,
			name,
			unitPrice,
			quantity
		);
	}

	public static ShoppingBasketProduct newInstanceWithCreatedAt(
		ShoppingBasketProductId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		UserId userId,
		Name name,
		Price unitPrice,
		Quantity quantity
	) {
		return new ShoppingBasketProduct(
			id,
			createdAt,
			productVariantId,
			userId,
			name,
			unitPrice,
			quantity
		);
	}

	public Price getTotalPrice() {
		return unitPrice.multiplyQuantity(quantity);
	}

	public void changeQuantity(
		UserId userId,
		Quantity quantity
	) {
		if (!isOwner(userId)) {
			throw new ProductException("UserId is not the same");
		}
		validateQuantity(quantity);
		this.quantity = quantity;
	}

	public boolean isOwner(UserId userId) {
		return this.userId.equals(userId);
	}

	public ShoppingBasketProductId getShoppingBasketProductId() {
		return internalId();
	}

	@Override
	public String toString() {
		return "CartProduct{" +
			"name=" + name.getValue() +
			'}';
	}

	private void validateQuantity(Quantity quantity) {
		if (quantity.isLessThan(new Quantity(BigInteger.valueOf(1)))) {
			throw new ProductException("수량이 1보다 크거나 같아야 합니다");
		}
	}

}
