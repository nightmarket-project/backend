package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.valueobject.ItemOptionGroupId;
import store.nightmarket.itemcore.valueobject.ItemOptionId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemOptionGroup extends BaseModel<ItemOptionGroupId> {

    @Getter
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

    // 전체 옵션 그룹의 수량 일괄 차감
    public void reduceOptionGroupQuantityBy(UserItemOptionGroup buyOptionGroup) {
        Map<ItemOptionId, ItemOption> itemOptionMap = itemOptions.stream()
                .collect(Collectors.toMap(ItemOption::getOptionId, Function.identity()));

        buyOptionGroup.getUserItemOptions()
                .forEach(buyItemOption -> {
                    ItemOption option = itemOptionMap.get(buyItemOption.getOptionId());
                    if (option != null) {
                        option.reduceOptionQuantityBy(buyItemOption);
                    }
                });
    }

    public Optional<List<ErrorResult>> findOptionGroupErrors(UserItemOptionGroup buyOptionGroup) {
        List<ErrorResult> errors = new ArrayList<>();
        Map<ItemOptionId, ItemOption> itemOptionMap = itemOptions.stream()
                .collect(Collectors.toMap(ItemOption::getOptionId, Function.identity()));

        buyOptionGroup.getUserItemOptions()
                .forEach(buyProductItemOption -> {
                    ItemOption option = itemOptionMap.get(buyProductItemOption.getOptionId());
                    if (option != null) {
                        option.findOptionErrors(buyProductItemOption)
                                .ifPresent(errors::addAll);
                    }
                });

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

}
