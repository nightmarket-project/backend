package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.ProductId;

public class OptionGroup extends BaseModel<OptionGroupId> {

    private ProductId productId;
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

    public OptionGroup newInstance(
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
