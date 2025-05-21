package store.nightmarket.itemcore.model;

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
    private Map<ItemDetailOptionId, ItemDetailOption> itemDetailOptions;

    private ItemOption(
            ItemOptionId id,
            Name name,
            List<ItemDetailOption> itemDetailOptionList // 창고 있는 물품
    ) {
        super(id);
        this.name = name;
        this.itemDetailOptions = itemDetailOptionList.stream()
                .collect(Collectors.toMap(ItemDetailOption::getDetailOptionId, Function.identity()));
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
       buyUserOption.getUserItemDetailOptions()
                .forEach(buyDetailOption -> {
                    ItemDetailOption itemDetailOption = itemDetailOptions.get(buyDetailOption.getDetailOptionId());
                    if (itemDetailOption != null) {
                        itemDetailOption.reduceDetailOptionQuantityBy(buyDetailOption);
                    }
                });
    }

    public Optional<List<ErrorResult>> findOptionErrors(UserItemOption buyUserOption) {
        List<ErrorResult> errors = new ArrayList<>();

        buyUserOption.getUserItemDetailOptions()
                .forEach(buyDetailOption -> {
                    ItemDetailOption option = itemDetailOptions.get(buyDetailOption.getDetailOptionId());
                    if (option != null) {
                        option.findDetailOptionError(buyDetailOption)
                                .ifPresent(errors::add);
                    }
                });

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemOption other = (ItemOption) obj;
        return Objects.equals(getOptionId(), other.getOptionId())
                && Objects.equals(name, other.name)
                && Objects.equals(itemDetailOptions, other.itemDetailOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOptionId(), name, itemDetailOptions);
    }

}
