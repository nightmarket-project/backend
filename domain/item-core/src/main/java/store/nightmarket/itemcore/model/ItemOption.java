package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemOption extends BaseModel<ItemOptionId> {

    private Name name;
    @Getter
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

    public ItemOptionId getOptionId() {
        return internalId();
    }

    //옵션 내 재고(상세 옵션들) 차감
    public void reduceOptionQuantityBy(UserItemOption buyUserOption) {
        Map<ItemDetailOptionId, ItemDetailOption> itemDetailOptionMap = itemDetailOptions.stream()
                .collect(Collectors.toMap(ItemDetailOption::getDetailOptionId, Function.identity()));

        buyUserOption.getUserItemDetailOptions()
                .forEach(buyDetailOption -> {
                    ItemDetailOption itemDetailOption = itemDetailOptionMap.get(buyDetailOption.getDetailOptionId());
                    if (itemDetailOption != null) {
                        itemDetailOption.reduceDetailOptionQuantityBy(buyDetailOption);
                    }
                });
    }

    public Optional<List<ErrorResult>> findOptionErrors(UserItemOption buyUserOption) {
        List<ErrorResult> errors = new ArrayList<>();
        Map<ItemDetailOptionId, ItemDetailOption> itemDetailOptionsMap = itemDetailOptions.stream()
                .collect(Collectors.toMap(ItemDetailOption::getDetailOptionId, Function.identity()));

        buyUserOption.getUserItemDetailOptions()
                .forEach(buyDetailOption -> {
                    ItemDetailOption option = itemDetailOptionsMap.get(buyDetailOption.getDetailOptionId());
                    if (option != null) {
                        option.findDetailOptionError(buyDetailOption)
                                .ifPresent(errors::add);
                    }
                });

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

}
