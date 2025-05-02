package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.ItemOptionCombinationId;

import java.util.List;

public class ItemOptionCombination extends BaseModel<ItemOptionCombinationId> {

    private ItemId itemId;
    private List<ItemOptionGroup> itemOptionGroups;

    private ItemOptionCombination(
            ItemOptionCombinationId id,
            ItemId itemId,
            List<ItemOptionGroup> itemOptionGroups) {
        super(id);
        this.itemId = itemId;
        this.itemOptionGroups = itemOptionGroups;
    }

    public static ItemOptionCombination newInstance(
            ItemOptionCombinationId id,
            ItemId itemId,
            List<ItemOptionGroup> itemOptionGroups
    ) {
        return new ItemOptionCombination(
                id,
                itemId,
                itemOptionGroups
        );
    }

}
