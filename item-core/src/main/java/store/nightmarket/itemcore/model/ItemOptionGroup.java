package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;
import store.nightmarket.itemcore.valueobject.Name;

import java.util.List;

public class ItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private Name name;
    private List<ItemOption> itemOptions;

    private ItemOptionGroup(
            ItemOptionGroupId id,
            Name name,
            List<ItemOption> itemOptions
    ) {
        super(id);
        this.name = name;
        this.itemOptions = itemOptions;
    }

    public static ItemOptionGroup newInstance(
            ItemOptionGroupId id,
            Name name,
            List<ItemOption> itemOptions
    ) {
        return new ItemOptionGroup(id, name, itemOptions);
    }
}
