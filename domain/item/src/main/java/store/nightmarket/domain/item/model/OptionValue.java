package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;

public class OptionValue extends BaseModel<OptionValueId> {

    private final OptionGroupId groupId;
    private String value;
    private Price price;
    private int order;

    private OptionValue(
        OptionValueId id,
        OptionGroupId groupId,
        String value,
        Price price,
        int order
    ) {
        super(id);
        this.groupId = groupId;
        this.value = value;
        this.price = price;
        this.order = order;
    }

    public static OptionValue newInstance(
        OptionValueId id,
        OptionGroupId groupId,
        String value,
        Price price,
        int order
    ) {
        return new OptionValue(
            id,
            groupId,
            value,
            price,
            order
        );
    }

}
