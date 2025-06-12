package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.OptionValueId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.VariantOptionValueId;

public class VariantOptionValue extends BaseModel<VariantOptionValueId> {

    private ProductVariantId productVariantId;
    private OptionGroupId optionGroupId;
    private OptionValueId optionValueId;

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

    public VariantOptionValue newInstance(
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

}
