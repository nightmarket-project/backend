package store.nightmarket.itemweb.model;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

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

	public ProductPostId getProductPostId() {
		return internalId();
	}

}
