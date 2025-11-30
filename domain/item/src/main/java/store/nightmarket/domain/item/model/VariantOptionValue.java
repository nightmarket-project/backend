package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;

@Getter
public class VariantOptionValue extends BaseModel<VariantOptionValueId> {

	private final ProductVariantId productVariantId;
	private final OptionGroupId optionGroupId;
	private final OptionValueId optionValueId;

	private VariantOptionValue(
		VariantOptionValueId id,
		ProductVariantId productVariantId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {
		super(id);
		this.productVariantId = productVariantId;
		this.optionGroupId = optionGroupId;
		this.optionValueId = optionValueId;
	}

	private VariantOptionValue(
		VariantOptionValueId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {
		super(id, createdAt);
		this.productVariantId = productVariantId;
		this.optionGroupId = optionGroupId;
		this.optionValueId = optionValueId;
	}

	public static VariantOptionValue newInstance(
		VariantOptionValueId id,
		ProductVariantId productVariantId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {
		return new VariantOptionValue(
			id,
			productVariantId,
			optionGroupId,
			optionValueId
		);
	}

	public static VariantOptionValue newInstanceWithCreatedAt(
		VariantOptionValueId id,
		LocalDateTime createdAt,
		ProductVariantId productVariantId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId
	) {
		return new VariantOptionValue(
			id,
			createdAt,
			productVariantId,
			optionGroupId,
			optionValueId
		);
	}

	public VariantOptionValueId getVariantOptionValueId() {
		return internalId();
	}

}
