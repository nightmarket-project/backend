package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;

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

	public VariantOptionValueId getVariantOptionValueId() {
		return internalId();
	}

}
