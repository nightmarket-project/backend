package store.nightmarket.domain.item.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.ProductId;

public class OptionGroup extends BaseModel<OptionGroupId> {

    private final ProductId productId;
    private Name name;
    private int order;
    private List<OptionValue> optionValueList;

    private OptionGroup(
        OptionGroupId id,
        ProductId productId,
        Name name,
        int order,
        List<OptionValue> optionValueList
    ) {
        super(id);
        this.productId = productId;
        this.name = name;
        this.order = order;
        this.optionValueList = optionValueList;
    }

    public static OptionGroup newInstance(
        OptionGroupId id,
        ProductId productId,
        Name name,
        int order,
        List<OptionValue> optionValueList
    ) {
        return new OptionGroup(
            id,
            productId,
            name,
            order,
            optionValueList
        );
    }

}
