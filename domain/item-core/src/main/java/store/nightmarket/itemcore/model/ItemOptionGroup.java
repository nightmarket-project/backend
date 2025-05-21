package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;
import store.nightmarket.itemcore.valueobject.ItemOptionId;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    private Map<ItemOptionId, ItemOption> itemOptions;

    private ItemOptionGroup(
            ItemOptionGroupId id,
            List<ItemOption> itemOptionList
    ) {
        super(id);
        this.itemOptions = itemOptionList.stream()
                .collect(Collectors.toMap(ItemOption::getOptionId, Function.identity()));
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

    public ItemOptionGroupId getOptionGroupId() {
        return internalId();
    }

    // 전체 옵션 그룹의 수량 일괄 차감
    public void reduceOptionGroupQuantityBy(UserItemOptionGroup buyOptionGroup) {
        buyOptionGroup.getUserItemOptions()
                .forEach(buyItemOption -> {
                    ItemOption option = itemOptions.get(buyItemOption.getOptionId());
                    if (option != null) {
                        option.reduceOptionQuantityBy(buyItemOption);
                    }
                });
    }

    public List<ItemOptionValidationError> findOptionGroupErrors(UserItemOptionGroup buyOptionGroup) {
        List<ItemOptionValidationError> errors = new ArrayList<>();
        buyOptionGroup.getUserItemOptions()
                .forEach(buyProductItemOption -> {
                    ItemOption option = itemOptions.get(buyProductItemOption.getOptionId());
                    if (option != null) {
                        errors.addAll(option.findOptionErrors(buyProductItemOption));
                    } else {
                        throw new ItemCoreException("No option found for option id " + buyProductItemOption.getOptionId());
                    }
                });

        return errors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemOptionGroup other = (ItemOptionGroup) obj;
        return  Objects.equals(getOptionGroupId(), other.getOptionGroupId())
                && Objects.equals(itemOptions, other.itemOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemOptions);
    }

}
