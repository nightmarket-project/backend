package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;

import java.util.List;

public class UserItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private ItemId itemId;
    private List<ItemOption> itemOptions;

    private UserItemOptionGroup(
            ItemOptionGroupId groupId,
            ItemId itemId,
            List<ItemOption> itemOptions
    ) {
        super(groupId);
        this.itemId = itemId;
        this.itemOptions = itemOptions;
    }

    private UserItemOptionGroup newInstance(
            ItemOptionGroupId groupId,
            ItemId itemId,
            List<ItemOption> itemOptions
    ) {
        return new UserItemOptionGroup(
                groupId,
                itemId,
                itemOptions
        );
    }

}
