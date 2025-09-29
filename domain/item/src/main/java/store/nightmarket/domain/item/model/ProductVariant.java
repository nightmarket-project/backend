package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

@Getter
public class ProductVariant extends BaseModel<ProductVariantId> {

	private final ProductId productId;
	private final UserId seller;
	private final String SKUCode;
	private Quantity quantity;

	private ProductVariant(
		ProductVariantId id,
		ProductId productId,
		UserId seller,
		String SKUCode,
		Quantity quantity
	) {
		super(id);
		this.productId = productId;
		this.seller = seller;
		this.SKUCode = SKUCode;
		this.quantity = quantity;
	}

	private ProductVariant(
		ProductVariantId id,
		LocalDateTime createdAt,
		ProductId productId,
		UserId seller,
		String SKUCode,
		Quantity quantity
	) {
		super(id, createdAt);
		this.productId = productId;
		this.seller = seller;
		this.SKUCode = SKUCode;
		this.quantity = quantity;
	}

	public static ProductVariant newInstance(
		ProductVariantId id,
		ProductId productId,
		UserId seller,
		String SKUCode,
		Quantity quantity
	) {
		return new ProductVariant(
			id,
			productId,
			seller,
			SKUCode,
			quantity
		);
	}

	public static ProductVariant newInstanceWithCreatedAt(
		ProductVariantId id,
		LocalDateTime createdAt,
		ProductId productId,
		UserId seller,
		String SKUCode,
		Quantity quantity
	) {
		return new ProductVariant(
			id,
			createdAt,
			productId,
			seller,
			SKUCode,
			quantity
		);
	}

	public ProductVariantId getProductVariantId() {
		return internalId();
	}

	public boolean isNotSameAsProduct(ProductVariantId purchaseProductId) {
		return !getProductVariantId().equals(purchaseProductId);
	}

	public void purchase(Quantity purchaseQuantity) {
		if (this.isNotAbleToPurchase(purchaseQuantity)) {
			throw new QuantityException("구매 불가 상품입니다.");
		}
		quantity = quantity.subtract(purchaseQuantity);
	}

	public void restoreQuantity(Quantity restoreQuantity) {
		quantity = quantity.add(restoreQuantity);
	}

	private boolean isNotAbleToPurchase(Quantity purchaseQuantity) {
		return quantity.isLessThan(purchaseQuantity);
	}

}
