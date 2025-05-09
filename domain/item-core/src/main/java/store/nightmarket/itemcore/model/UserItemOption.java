package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionId;

import java.util.List;

@Getter
public class UserItemOption extends BaseModel<ItemOptionId> {

    private List<UserItemDetailOption> userItemDetailOptions;

    private UserItemOption(
            ItemOptionId optionId,
            List<UserItemDetailOption> userItemDetailOptions // 창고 있는 물품
    ) {
        super(optionId);
        this.userItemDetailOptions = userItemDetailOptions;
    }

    public static UserItemOption newInstance(
            ItemOptionId optionId,
            List<UserItemDetailOption> userItemDetailOptions
    ) {
        return new UserItemOption(optionId, userItemDetailOptions);
    }

    public ItemOptionId getOptionId() {
        return internalId();
    }
}
