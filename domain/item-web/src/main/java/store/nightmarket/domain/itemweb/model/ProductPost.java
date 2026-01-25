package store.nightmarket.domain.itemweb.model;

import java.time.LocalDateTime;

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
	private LocalDateTime publishAt;
	private LocalDateTime expiredAt;

	private ProductPost(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		super(id, ImageOwnerType.PRODUCT_POST);
		this.productId = productId;
		this.rating = rating;
		this.state = state;
		this.publishAt = publishAt;
		this.expiredAt = expiredAt;
	}

	private ProductPost(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		super(id, createdAt, ImageOwnerType.PRODUCT_POST);
		this.productId = productId;
		this.rating = rating;
		this.state = state;
		this.publishAt = publishAt;
		this.expiredAt = expiredAt;
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
			state,
			null,
			null
		);
	}

	public static ProductPost newInstance(
		ProductPostId id,
		ProductId productId,
		Rating rating,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		return new ProductPost(
			id,
			productId,
			rating,
			state,
			publishAt,
			expiredAt
		);
	}

	public static ProductPost newInstanceWithCreatedAt(
		ProductPostId id,
		LocalDateTime createdAt,
		ProductId productId,
		Rating rating,
		PostState state,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		return new ProductPost(
			id,
			createdAt,
			productId,
			rating,
			state,
			publishAt,
			expiredAt
		);
	}

	public ProductPostId getProductPostId() {
		return internalId();
	}

	public void schedule(
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {
		if (publishAt == null) {
			throw new ProductPostException("publishAt must not be null");
		}

		if (publishAt.isBefore(LocalDateTime.now())) {
			throw new ProductPostException("publish time is past");
		}

		if (expiredAt != null && publishAt.isAfter(expiredAt)) {
			throw new ProductPostException("publishAt must be before expireAt");
		}

		if (!state.canTransitionTo(PostState.SCHEDULED)) {
			throw new ProductPostException("cannot change state to schedule");
		}

		this.publishAt = publishAt;
		this.expiredAt = expiredAt;
		this.state = PostState.SCHEDULED;
	}

	public void delete() {
		if (!state.canTransitionTo(PostState.DELETED)) {
			throw new ProductPostException("cannot change state to deleted");
		}
		this.state = PostState.DELETED;
	}

	public void refreshStatus(LocalDateTime now) {
		if (isNotPosted()) {
			return;
		}

		if (shouldExpire(now)) {
			state = PostState.EXPIRED;
			return;
		}

		if (shouldPublish(now)) {
			state = PostState.PUBLISHED;
		}
	}

	private boolean isNotPosted() {
		return state == PostState.DELETED || state == PostState.DRAFT;
	}

	private boolean shouldPublish(LocalDateTime now) {
		return state == PostState.SCHEDULED
			&& !publishAt.isAfter(now);
	}

	private boolean shouldExpire(LocalDateTime now) {
		return state == PostState.PUBLISHED
			&& expiredAt != null
			&& !expiredAt.isAfter(now);
	}

}
