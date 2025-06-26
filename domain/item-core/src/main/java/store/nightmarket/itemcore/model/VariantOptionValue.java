package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.OptionValueId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.VariantOptionValueId;

public class VariantOptionValue extends BaseModel<VariantOptionValueId> {

    private ProductVariantId productVariantId;
    private OptionGroup optionGroup;
    private OptionValue optionValue;

    private VariantOptionValue(
        VariantOptionValueId id,
        ProductVariantId productVariantId,
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        super(id);
        this.productVariantId = productVariantId;
        this.optionGroup = optionGroup;
        this.optionValue = optionValue;
    }

    public static VariantOptionValue newInstance(
        VariantOptionValueId id,
        ProductVariantId productVariantId,
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        return new VariantOptionValue(
            id,
            productVariantId,
            optionGroup,
            optionValue
        );
    }

}
