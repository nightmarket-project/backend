package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;

public class VariantOptionValue extends BaseModel<VariantOptionValueId> {

    private final ProductVariantId productVariantId;
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
