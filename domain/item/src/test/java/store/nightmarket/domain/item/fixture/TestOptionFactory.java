package store.nightmarket.domain.item.fixture;

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

    public static ItemDetailOption defaultDetailOption() {
        return createDetailOption(
                UUID.randomUUID(),
                "블랙",
                1000,
                100
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

    public static ItemOptionGroup defaultOptionGroup() {
        return createItemOptionGroup(
                UUID.randomUUID(),
                defaultOption(),
                createItemOption(
                        UUID.randomUUID(),
                        "cpu",
                        createDetailOption(
                                UUID.randomUUID(),
                                "4core",
                                20000,
                                100
                        ), createDetailOption(
                                UUID.randomUUID(),
                                "8core",
                                40000,
                                500
                        )
                )
        );
    }

}
