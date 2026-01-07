package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageOwnerType;
import store.nightmarket.domain.itemweb.valueobject.Rating;

@Getter
public class ProductPost extends ImageOwnerModel<ProductPostId> {

	private final ProductId productId;
	private Rating rating;
	private boolean deleted;

	private ProductPost(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		super(
			id,
			ImageOwnerType.PRODUCT_POST
		);
		this.productId = productId;
		this.rating = rating;
		this.deleted = deleted;
	}

	private ProductPost(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		super(
			id,
			createdAt,
			ImageOwnerType.PRODUCT_POST
		);
		this.productId = productId;
		this.rating = rating;
		this.deleted = deleted;
	}

	public static ProductPost newInstance(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		return new ProductPost(
			id,
			productId,
			rating,
			deleted
		);
	}

	public static ProductPost newInstanceWithCreatedAt(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		return new ProductPost(
			id,
			createdAt,
			productId,
			rating,
			deleted
		);
	}

	public ProductPostId getProductPostId() {
		return internalId();
	}

}
