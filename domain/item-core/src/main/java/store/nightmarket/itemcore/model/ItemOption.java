package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;

import java.util.List;

public class ItemOption extends BaseModel<ItemOptionId> {

    private Name name;
    private List<ItemDetailOption> itemDetailOptions;

    private ItemOption(
            ItemOptionId id,
            Name name,
            List<ItemDetailOption> itemDetailOptions // 창고 있는 물품
    ) {
        super(id);
        this.name = name;
        this.itemDetailOptions = itemDetailOptions;
    }

    public static ItemOption newInstance(
            ItemOptionId id,
            Name name,
            List<ItemDetailOption> itemDetailOptions
    ) {
        return new ItemOption(id, name, itemDetailOptions);
    }

    public void isAvailableToBuy(ItemOption buyGroup) {

        for (int i = 0; i < itemDetailOptions.size(); i++) {
            ItemDetailOption itemDetailOption = itemDetailOptions.get(i);
            ItemDetailOption itemDetailOptionUserBuy = buyGroup.itemDetailOptions.get(i);

            itemDetailOption.isAvailableToBuy(itemDetailOptionUserBuy);
        }
    }
}
