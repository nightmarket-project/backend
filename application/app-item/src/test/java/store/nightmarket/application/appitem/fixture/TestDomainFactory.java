package store.nightmarket.application.appitem.fixture;

import java.math.BigInteger;
import java.util.UUID;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.model.id.ImageManagerId;
import store.nightmarket.itemweb.model.id.ImageOwnerId;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.itemweb.model.id.ReplyId;
import store.nightmarket.itemweb.model.id.ReviewId;
import store.nightmarket.itemweb.model.state.ImageType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Image;
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
			new Price(BigInteger.valueOf(10000))
		);
	}

	public static ImageManager createImageManager(
		UUID imageManagerId,
		ImageType imageType,
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

	public static Review createReview(
		UUID reviewId,
		UUID productPostId,
		UUID userId
	) {
		return Review.newInstance(
			new ReviewId(reviewId),
			new ProductPostId(productPostId),
			new UserId(userId),
			new CommentText("내용1"),
			new Rating(4.5f)
		);
	}

	public static Reply createReply(
		UUID replyId,
		UUID userId,
		UUID reviewId
	) {
		return Reply.newInstance(
			new ReplyId(replyId),
			new CommentText("내용2"),
			new UserId(userId),
			new ReviewId(reviewId)
		);
	}

	public static User createUser(
		UUID userId
	) {
		return User.newInstance(
			new UserId(userId),
			new Name("이름1")
		);
	}

	public static OptionGroup createOptionGroup(
		UUID optionGroupId,
		UUID productId
	) {
		return OptionGroup.newInstance(
			new OptionGroupId(optionGroupId),
			new ProductId(productId),
			new Name("그룹 이름1"),
			1
		);
	}

	public static OptionValue createOptionValue(
		UUID optionValueId,
		UUID optionGroupId
	) {
		return OptionValue.newInstance(
			new OptionValueId(optionValueId),
			new OptionGroupId(optionGroupId),
			new Name("옵션 이름"),
			new Price(BigInteger.valueOf(1000)),
			1
		);
	}

	public static ProductVariant createProductVariant(
		UUID productVariantId,
		UUID productId
	) {
		return ProductVariant.newInstance(
			new ProductVariantId(productVariantId),
			new ProductId(productId),
			new UserId(UUID.randomUUID()),
			"1111",
			new Quantity(BigInteger.valueOf(100))
		);
	}

	public static VariantOptionValue createVariantOptionValue(
		UUID variantOptionValueId,
		UUID productVariantId,
		UUID optionGroupId,
		UUID optionValueId
	) {
		return VariantOptionValue.newInstance(
			new VariantOptionValueId(variantOptionValueId),
			new ProductVariantId(productVariantId),
			new OptionGroupId(optionGroupId),
			new OptionValueId(optionValueId)
		);
	}

	public static ShoppingBasketProduct createShoppingBasketProduct(
		UUID shoppingBasketProductId,
		UUID userId,
		UUID productVariantId,
		BigInteger quantity
	) {
		return ShoppingBasketProduct.newInstance(
			new ShoppingBasketProductId(shoppingBasketProductId),
			new ProductVariantId(productVariantId),
			new UserId(userId),
			new Name("상품1"),
			new Price(BigInteger.valueOf(1000)),
			new Quantity(quantity)
		);
	}

}
