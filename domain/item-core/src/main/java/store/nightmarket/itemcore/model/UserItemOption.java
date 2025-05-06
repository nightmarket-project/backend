package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;

import java.util.List;

public class UserItemOption extends BaseModel<ItemOptionId> {

    private List<ItemDetailOption> itemDetailOptions;

    private UserItemOption(
            ItemOptionId optionId,
            List<ItemDetailOption> itemDetailOptions // 창고 있는 물품
    ) {
        super(optionId);
        this.itemDetailOptions = itemDetailOptions;
    }

    public static UserItemOption newInstance(
            ItemOptionId optionId,
            List<ItemDetailOption> itemDetailOptions
    ) {
        return new UserItemOption(optionId, itemDetailOptions);
    }

}
