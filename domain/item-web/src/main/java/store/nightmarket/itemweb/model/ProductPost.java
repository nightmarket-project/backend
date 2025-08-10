package store.nightmarket.itemweb.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

@Getter
public class ProductPost extends BaseModel<ProductPostId> {

	private final Product product;
	private List<ImageManager> imageManagerList;
	private Rating rating;
	private boolean deleted;

	private ProductPost(
		ProductPostId id,
		Product product,
		Rating rating,
		List<ImageManager> imageManagerList
	) {
		super(id);
		this.product = product;
		this.imageManagerList = new ArrayList<>(imageManagerList);
		this.rating = rating;
		this.deleted = false;
	}

	public static ProductPost newInstance(
		ProductPostId id,
		Product product,
		Rating rating,
		List<ImageManager> imageManagerList
	) {
		return new ProductPost(
			id,
			product,
			rating,
			imageManagerList
		);
	}

	public ProductPostId getProductPostId() {
		return internalId();
	}

}
