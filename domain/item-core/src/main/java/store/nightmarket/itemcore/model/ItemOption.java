package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private ItemOptionId getOptionId() {
        return internalId();
    }

    public Optional<UserItemOption> isAvailableToBuy(UserItemOption buyGroup) {
        if(!getOptionId().equals(buyGroup.getOptionId()) ||
                buyGroup.getUserItemDetailOptions().isEmpty()) {
            return Optional.empty();
        }

        List<UserItemDetailOption> availableOptions = filterPurchasableOptions(buyGroup);
        UserItemOption purchasableOptions = UserItemOption.newInstance(
                buyGroup.getOptionId(),
                availableOptions
        );

        return Optional.of(purchasableOptions);
    }

    private List<UserItemDetailOption> filterPurchasableOptions(UserItemOption buyGroup) {
        List<UserItemDetailOption> userItemDetailOptions = buyGroup.getUserItemDetailOptions();

        return itemDetailOptions.stream()
                .flatMap(itemDetailOption ->
                        userItemDetailOptions.stream()
                                .map(itemDetailOption::isAvailableToBuy)
                                .flatMap(Optional::stream))
                .collect(Collectors.toList());
    }

}
