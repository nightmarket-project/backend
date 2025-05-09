package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private List<ItemOption> itemOptions;

    private ItemOptionGroup(
            ItemOptionGroupId id,
            List<ItemOption> itemOptions
    ) {
        super(id);
        this.itemOptions = itemOptions;
    }

    public static ItemOptionGroup newInstance(
            ItemOptionGroupId id,
            List<ItemOption> itemOptions
    ) {
        return new ItemOptionGroup(
                id,
                itemOptions
        );
    }

    public UserItemOptionGroup isAvailableToBuy(
            UserItemOptionGroup buyGroup
    ) {
        List<UserItemOption> availableOptions = filterPurchasableOptions(buyGroup);

        return UserItemOptionGroup.newInstance(
                buyGroup.getOptionGroupId(),
                availableOptions
        );
    }

    private List<UserItemOption> filterPurchasableOptions(UserItemOptionGroup buyGroup) {
        List<UserItemOption> userItemOptions = buyGroup.getUserItemOptions();

        return itemOptions.stream()
                .flatMap(itemOption ->
                        userItemOptions.stream()
                                .map(itemOption::isAvailableToBuy)
                                .flatMap(Optional::stream))
                .collect(Collectors.toList());
    }

}
