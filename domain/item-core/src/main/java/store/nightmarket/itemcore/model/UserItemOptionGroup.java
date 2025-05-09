package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;

import java.util.List;

@Getter
public class UserItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private List<UserItemOption> userItemOptions;

    private UserItemOptionGroup(
            ItemOptionGroupId groupId,
            List<UserItemOption> userItemOptions
    ) {
        super(groupId);
        this.userItemOptions = userItemOptions;
    }

    public static UserItemOptionGroup newInstance(
            ItemOptionGroupId groupId,
            List<UserItemOption> userItemOptions
    ) {
        return new UserItemOptionGroup(
                groupId,
                userItemOptions
        );
    }

    public ItemOptionGroupId getOptionGroupId() {
        return internalId();
    }

}
