package store.nightmarket.itemcore.fixture;

import store.nightmarket.itemcore.model.ItemDetailOption;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestOptionFactory {

    public static ItemDetailOption createDetailOption(
            UUID id,
            String detailOptionName,
            int price,
            int quantity
    ) {
        return ItemDetailOption.newInstance(
                new ItemDetailOptionId(id),
                new Name(detailOptionName),
                new Price(new BigDecimal(price)),
                new Quantity(new BigDecimal(quantity))
        );
    }

    public static ItemOption createItemOption(
            UUID id,
            String optionName,
            List<ItemDetailOption> detailOptions
    ) {
        return ItemOption.newInstance(
                new ItemOptionId(id),
                new Name(optionName),
                new ArrayList<>(detailOptions)
        );
    }

    public static ItemOptionGroup createItemOptionGroup(
            UUID groupId,
            List<ItemOption> options
    ) {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(groupId),
                new ArrayList<>(options)
        );
    }

}
