package store.nightmarket.domain.order.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.order.valueobject.Price;
import store.nightmarket.domain.order.valueobject.ProductVariantId;

@Getter
public class ProductVariant extends BaseModel<ProductVariantId> {

	private Price price;

	private ProductVariant(
		ProductVariantId id,
		Price price
	) {
		super(id);
		this.price = price;
	}

	private ProductVariant(
		ProductVariantId id,
		LocalDateTime createdAt,
		Price price
	) {
		super(id, createdAt);
		this.price = price;
	}

	public static ProductVariant newInstance(
		ProductVariantId productVariantId,
		Price price
	) {
		return new ProductVariant(
			productVariantId,
			price
		);
	}

	public static ProductVariant newInstanceWithCreatedAt(
		ProductVariantId productVariantId,
		LocalDateTime createdAt,
		Price price
	) {
		return new ProductVariant(
			productVariantId,
			createdAt,
			price
		);
	}

	public ProductVariantId getProductVariantId() {
		return internalId();
	}

}
