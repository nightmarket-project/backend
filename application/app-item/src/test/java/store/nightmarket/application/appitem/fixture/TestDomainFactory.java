package store.nightmarket.application.appitem.fixture;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageManagerId;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

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
			new Rating(4.5f),
			LocalDateTime.now()
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
			new ReviewId(reviewId),
			LocalDateTime.now()
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
			"옵션 이름",
			new Price(BigDecimal.valueOf(1000)),
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
			new Quantity(new BigDecimal(100))
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
		BigDecimal quantity
	) {
		return ShoppingBasketProduct.newInstance(
			new ShoppingBasketProductId(shoppingBasketProductId),
			new ProductVariantId(productVariantId),
			new UserId(userId),
			new Name("상품1"),
			new Price(BigDecimal.valueOf(1000.0)),
			new Quantity(quantity)
		);
	}

}
