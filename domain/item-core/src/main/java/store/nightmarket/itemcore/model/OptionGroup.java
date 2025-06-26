package store.nightmarket.itemcore.model;

import java.util.ArrayList;
import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.ProductId;

public class OptionGroup extends BaseModel<OptionGroupId> {

    private ProductId productId;
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
