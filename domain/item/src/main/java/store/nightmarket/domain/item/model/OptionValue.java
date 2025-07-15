package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;

public class OptionValue extends BaseModel<OptionValueId> {

    private String value;
    private Price price;
    private int order;

    private OptionValue(
        OptionValueId id,
        String value,
        Price price,
        int order
    ) {
        super(id);
        this.value = value;
        this.price = price;
        this.order = order;
    }

    public static OptionValue newInstance(
        OptionValueId id,
        String value,
        Price price,
        int order
    ) {
        return new OptionValue(
            id,
            value,
            price,
            order
        );
    }

}
