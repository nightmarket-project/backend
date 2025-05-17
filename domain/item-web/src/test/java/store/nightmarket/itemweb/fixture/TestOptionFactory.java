package store.nightmarket.itemweb.fixture;

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
            ItemDetailOption... detailOptions
    ) {
        return ItemOption.newInstance(
                new ItemOptionId(id),
                new Name(optionName),
                new ArrayList<>(List.of(detailOptions))
        );
    }

    public static ItemOptionGroup createItemOptionGroup(
            UUID groupId,
            ItemOption... options
    ) {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(groupId),
                new ArrayList<>(List.of(options))
        );
    }

    public static ItemOption defaultOption() {
        return createItemOption(
                UUID.randomUUID(),
                "색상",
                createDetailOption(
                        UUID.randomUUID(),
                        "블랙",
                        1000,
                        100
                ), createDetailOption(
                        UUID.randomUUID(),
                        "화이트",
                        2000,
                        200
                )

        );
    }
    
}
