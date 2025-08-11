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
	private List<ProductPostImageManager> productPostImageManagerList;
	private Rating rating;
	private boolean deleted;

	private ProductPost(
		ProductPostId id,
		Product product,
		Rating rating,
		List<ProductPostImageManager> productPostImageManagerList
	) {
		super(id);
		this.product = product;
		this.productPostImageManagerList = new ArrayList<>(productPostImageManagerList);
		this.rating = rating;
		this.deleted = false;
	}

	public static ProductPost newInstance(
		ProductPostId id,
		Product product,
		Rating rating,
		List<ProductPostImageManager> productPostImageManagerList
	) {
		return new ProductPost(
			id,
			product,
			rating,
			productPostImageManagerList
		);
	}

	public ProductPostId getProductPostId() {
		return internalId();
	}

}
