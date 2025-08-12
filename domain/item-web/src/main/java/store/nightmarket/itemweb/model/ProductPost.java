package store.nightmarket.itemweb.model;

import java.util.List;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.state.ImageOwnerType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

@Getter
public class ProductPost extends ImageOwnerModel<ProductPostId> {

	private final ProductId productId;
	private Rating rating;
	private boolean deleted;

	private ProductPost(
		ProductPostId id,
		ImageOwnerId imageOwnerId,
		List<ImageManager> imageManagerList,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		super(
			id,
			imageOwnerId,
			ImageOwnerType.PRODUCT_POST,
			imageManagerList
		);
		this.productId = productId;
		this.rating = rating;
		this.deleted = deleted;
	}

	public static ProductPost newInstance(
		ProductPostId id,
		ImageOwnerId imageOwnerId,
		List<ImageManager> imageManagerList,
		ProductId productId,
		Rating rating,
		boolean deleted
	) {
		return new ProductPost(
			id,
			imageOwnerId,
			imageManagerList,
			productId,
			rating,
			deleted
		);
	}

}
