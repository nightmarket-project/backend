package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.OptionValueId;

public class OptionValue extends BaseModel<OptionValueId> {

    private OptionGroupId groupId;
    private String value;
    private int order;

    private OptionValue(
        OptionValueId id,
        OptionGroupId groupId,
        String value,
        int order
    ) {
        super(id);
        this.groupId = groupId;
        this.value = value;
        this.order = order;
    }

    public OptionValue newInstance(
        OptionValueId id,
        OptionGroupId groupId,
        String value,
        int order
    ) {
        return new OptionValue(
            id,
            groupId,
            value,
            order
        );
    }

}
