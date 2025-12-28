package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;

@Getter
public class OptionGroup extends BaseModel<OptionGroupId> {

	private final ProductId productId;
	private final UserId userId;
	private Name name;
	private int order;

	private OptionGroup(
		OptionGroupId id,
		ProductId productId,
		UserId userId,
		Name name,
		int order
	) {
		super(id);
		this.productId = productId;
		this.userId = userId;
		this.name = name;
		this.order = order;
	}

	private OptionGroup(
		OptionGroupId id,
		LocalDateTime createdAt,
		ProductId productId,
		UserId userId,
		Name name,
		int order
	) {
		super(id, createdAt);
		this.productId = productId;
		this.userId = userId;
		this.name = name;
		this.order = order;
	}

	public static OptionGroup newInstance(
		OptionGroupId id,
		ProductId productId,
		UserId userId,
		Name name,
		int order
	) {
		return new OptionGroup(
			id,
			productId,
			userId,
			name,
			order
		);
	}

	public static OptionGroup newInstanceWithCreatedAt(
		OptionGroupId id,
		LocalDateTime createdAt,
		ProductId productId,
		UserId userId,
		Name name,
		int order
	) {
		return new OptionGroup(
			id,
			createdAt,
			productId,
			userId,
			name,
			order
		);
	}

	public OptionGroupId getOptionGroupId() {
		return internalId();
	}

	public boolean isOwner(UserId userId) {
		return this.userId.equals(userId);
	}

}
