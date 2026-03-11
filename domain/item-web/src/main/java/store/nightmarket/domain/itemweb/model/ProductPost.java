package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageOwnerType;
import store.nightmarket.domain.itemweb.model.state.PostState;
import store.nightmarket.domain.itemweb.valueobject.Rating;

@Getter
public class ProductPost extends ImageOwnerModel<ProductPostId> {

	private final ProductId productId;
	private Rating rating;
	private PostState state;

	private ProductPost(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		PostState state
	) {
		super(id, ImageOwnerType.PRODUCT_POST);
		this.productId = productId;
		this.rating = rating;
		this.state = state;
	}

	private ProductPost(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		PostState state
	) {
		super(id, createdAt, ImageOwnerType.PRODUCT_POST);
		this.productId = productId;
		this.rating = rating;
		this.state = state;
	}

	public static ProductPost newInstance(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		PostState state
	) {
		return new ProductPost(
			id,
			productId,
			rating,
			state
		);
	}

	public static ProductPost newInstanceWithCreatedAt(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		PostState state
	) {
		return new ProductPost(
			id,
			createdAt,
			productId,
			rating,
			state
		);
	}

	public ProductPostId getProductPostId() {
		return internalId();
	}

	public void publish() {
		if (!state.canTransitionTo(PostState.PUBLISHED)) {
			throw new ProductPostException("cannot change state to published");
		}
		this.state = PostState.PUBLISHED;
	}

	public void unpublish() {
		if (!state.canTransitionTo(PostState.UNPUBLISHED)) {
			throw new ProductPostException("cannot change state to unpublished");
		}
		this.state = PostState.UNPUBLISHED;
	}

	public void delete() {
		if (!state.canTransitionTo(PostState.DELETED)) {
			throw new ProductPostException("cannot change state to deleted");
		}
		this.state = PostState.DELETED;
	}

	public void edit(Rating editRating) {
		this.rating = Optional.of(editRating).orElseGet(() -> rating);
	}

}
