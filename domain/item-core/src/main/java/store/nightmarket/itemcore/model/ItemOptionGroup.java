package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;

import java.util.List;


public class ItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private ItemId itemId;
    private List<ItemOption> itemOptions;

    private ItemOptionGroup(
            ItemOptionGroupId id,
            ItemId itemId,
            List<ItemOption> itemOptions) {
        super(id);
        this.itemId = itemId;
        this.itemOptions = itemOptions;
    }

    public static ItemOptionGroup newInstance(
            ItemOptionGroupId id,
            ItemId itemId,
            List<ItemOption> itemOptions
    ) {
        return new ItemOptionGroup(
                id,
                itemId,
                itemOptions
        );
    }

    public void isAvailableToBuy(ItemOptionGroup buyCombination) {
        for (int i = 0; i < itemOptions.size(); i++) {
            ItemOption itemOption = itemOptions.get(i);
            ItemOption itemOptionUserBuy = buyCombination.itemOptions.get(i);

            itemOption.isAvailableToBuy(itemOptionUserBuy);
        }
    }

}
