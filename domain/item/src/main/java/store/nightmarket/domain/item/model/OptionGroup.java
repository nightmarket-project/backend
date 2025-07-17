package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;

public class OptionGroup extends BaseModel<OptionGroupId> {

    private final ProductId productId;
    private Name name;
    private int order;

    private OptionGroup(
        OptionGroupId id,
        ProductId productId,
        Name name,
        int order
    ) {
        super(id);
        this.productId = productId;
        this.name = name;
        this.order = order;
    }

    public static OptionGroup newInstance(
        OptionGroupId id,
        ProductId productId,
        Name name,
        int order
    ) {
        return new OptionGroup(
            id,
            productId,
            name,
            order
        );
    }

}
