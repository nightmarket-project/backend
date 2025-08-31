package store.nightmarket.application.appitem.fixture;

import java.math.BigDecimal;
import java.util.UUID;

import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

public class TestDomainFactory {

	public static ProductPost createProductPost(
		UUID productPostId,
		UUID productId
	) {
		return ProductPost.newInstance(
			new ProductPostId(productPostId),
			new ProductId(productId),
			new Rating(4.5f),
			false
		);
	}

	public static Product createProduct(UUID productId) {
		return Product.newInstance(
			new ProductId(productId),
			new Name("상품"),
			"좋은 상품",
			new Price(BigDecimal.valueOf(10000))
		);
	}

	public static ImageManager createImageManager(
		UUID imageManagerId,
		DomainImageType imageType,
		int displayOrder,
		UUID imageOwnerId
	) {
		return ImageManager.newInstance(
			new ImageManagerId(imageManagerId),
			new Image("imageUrlSample" + imageOwnerId + ".jpg"),
			imageType,
			displayOrder,
			new ImageOwnerId(imageOwnerId)
		);
	}

}
