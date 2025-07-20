package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;

@Getter
public class OptionValue extends BaseModel<OptionValueId> {

    private final OptionGroupId optionGroupId;
    private String value;
    private Price price;
    private int order;

    private OptionValue(
        OptionValueId id,
        OptionGroupId optionGroupId,
        String value,
        Price price,
        int order
    ) {
        super(id);
        this.optionGroupId = optionGroupId;
        this.value = value;
        this.price = price;
        this.order = order;
    }

    public static OptionValue newInstance(
        OptionValueId id,
        OptionGroupId optionGroupId,
        String value,
        Price price,
        int order
    ) {
        return new OptionValue(
            id,
            optionGroupId,
            value,
            price,
            order
        );
    }

    public OptionValueId getOptionValueId() {
        return internalId();
    }

}
