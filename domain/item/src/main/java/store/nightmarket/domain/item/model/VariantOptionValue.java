package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;

public class VariantOptionValue extends BaseModel<VariantOptionValueId> {

    private OptionGroup optionGroup;
    private OptionValue optionValue;

    private VariantOptionValue(
        VariantOptionValueId id,
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        super(id);
        this.optionGroup = optionGroup;
        this.optionValue = optionValue;
    }

    public static VariantOptionValue newInstance(
        VariantOptionValueId id,
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        return new VariantOptionValue(
            id,
            optionGroup,
            optionValue
        );
    }

}
