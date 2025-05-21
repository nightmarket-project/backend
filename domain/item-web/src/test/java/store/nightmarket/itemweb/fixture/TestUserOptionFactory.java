package store.nightmarket.itemweb.fixture;

import store.nightmarket.itemcore.model.UserItemDetailOption;
import store.nightmarket.itemcore.model.UserItemOption;
import store.nightmarket.itemcore.model.UserItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUserOptionFactory {

    public static UserItemDetailOption createUserItemDetailOption(
            UUID detailId,
            int quantity
    ) {
        return UserItemDetailOption.newInstance(
                new ItemDetailOptionId(detailId),
                new Quantity(BigDecimal.valueOf(quantity))
        );
    }

    public static UserItemOption createUserItemOption(
            UUID optionId,
            List<UserItemDetailOption> userDetailOptions
    ) {
        return UserItemOption.newInstance(
                new ItemOptionId(optionId),
                new ArrayList<>(userDetailOptions)
        );
    }

    public static UserItemOptionGroup createUserItemOptionGroup(
            UUID groupId,
            List<UserItemOption> userOptions
    ) {
        return UserItemOptionGroup.newInstance(
                new ItemOptionGroupId(groupId),
                new ArrayList<>(userOptions)
        );
    }

}
