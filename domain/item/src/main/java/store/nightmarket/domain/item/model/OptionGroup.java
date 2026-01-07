package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.valueobject.Name;

@Getter
public class OptionGroup extends BaseModel<OptionGroupId> {

	private final ProductId productId;
	private Name name;
	private int order;

	private OptionGroup(
		OptionGroupId id,
		ProductId productId,
		Name name,
		int order
	) {
		super(id);
		this.productId = productId;
		this.name = name;
		this.order = order;
	}

	private OptionGroup(
		OptionGroupId id,
		LocalDateTime createdAt,
		ProductId productId,
		Name name,
		int order
	) {
		super(id, createdAt);
		this.productId = productId;
		this.name = name;
		this.order = order;
	}

	public static OptionGroup newInstance(
		OptionGroupId id,
		ProductId productId,
		Name name,
		int order
	) {
		return new OptionGroup(
			id,
			productId,
			name,
			order
		);
	}

	public static OptionGroup newInstanceWithCreatedAt(
		OptionGroupId id,
		LocalDateTime createdAt,
		ProductId productId,
		Name name,
		int order
	) {
		return new OptionGroup(
			id,
			createdAt,
			productId,
			name,
			order
		);
	}

	public OptionGroupId getOptionGroupId() {
		return internalId();
	}

	public void edit(
		Name editName,
		int editOrder
	) {
		this.name = Optional.of(editName).orElseGet(() -> name);
		this.order = Optional.of(editOrder).orElseGet(() -> order);
	}

}
