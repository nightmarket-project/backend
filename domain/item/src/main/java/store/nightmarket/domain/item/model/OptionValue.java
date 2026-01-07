package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

@Getter
public class OptionValue extends BaseModel<OptionValueId> {

	private final OptionGroupId optionGroupId;
	private Name name;
	private Price price;
	private int order;

	private OptionValue(
		OptionValueId id,
		OptionGroupId optionGroupId,
		Name name,
		Price price,
		int order
	) {
		super(id);
		this.optionGroupId = optionGroupId;
		this.name = name;
		this.price = price;
		this.order = order;
	}

	private OptionValue(
		OptionValueId id,
		LocalDateTime createdAt,
		OptionGroupId optionGroupId,
		Name name,
		Price price,
		int order
	) {
		super(id, createdAt);
		this.optionGroupId = optionGroupId;
		this.name = name;
		this.price = price;
		this.order = order;
	}

	public static OptionValue newInstance(
		OptionValueId id,
		OptionGroupId optionGroupId,
		Name name,
		Price price,
		int order
	) {
		return new OptionValue(
			id,
			optionGroupId,
			name,
			price,
			order
		);
	}

	public static OptionValue newInstanceWithCreatedAt(
		OptionValueId id,
		LocalDateTime createdAt,
		OptionGroupId optionGroupId,
		Name name,
		Price price,
		int order
	) {
		return new OptionValue(
			id,
			createdAt,
			optionGroupId,
			name,
			price,
			order
		);
	}

	public OptionValueId getOptionValueId() {
		return internalId();
	}

	public void edit(
		Name editName,
		Price edtiPrice,
		int editOrder
	) {
		this.name = Optional.of(editName).orElseGet(() -> name);
		this.price = Optional.of(edtiPrice).orElseGet(() -> price);
		this.order = Optional.of(editOrder).orElseGet(() -> order);
	}

}
