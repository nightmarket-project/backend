package store.nightmarket.itemcore.fixture;

import store.nightmarket.itemcore.model.ItemDetailOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestObjectFactory {

    public static ItemDetailOption createDetailOption(
            String name,
            int price,
            int quantity
    ) {
        return ItemDetailOption.newInstance(
                new ItemDetailOptionId(UUID.randomUUID()),
                new Name(name),
                new Price(new BigDecimal(price)),
                new Quantity(new BigDecimal(quantity))
        );
    }

    public static ItemOption createItemOption(
            String groupName,
            List<ItemDetailOption> itemDetailOptions
    ) {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()),
                new Name(groupName),
                new ArrayList<>(itemDetailOptions)
        );
    }

    public static ItemOptionGroup createItemOptionGroup(
            List<ItemOption> itemOptions
    ) {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()),
                new ItemId(UUID.randomUUID()),
                new ArrayList<>(itemOptions)
        );
    }

    public static ItemDetailOption defaultOption() {
        return TestObjectFactory.createDetailOption("블랙", 1000, 10);
    }

    public static ItemOption defaultGroup() {
        return TestObjectFactory.createItemOption(
                "색상",
                List.of(
                        TestObjectFactory.createDetailOption("블랙", 1000, 10),
                        TestObjectFactory.createDetailOption("화이트", 2000, 20)
                )
        );
    }

    public static ItemOptionGroup defaultCombination() {
        return TestObjectFactory.createItemOptionGroup(
                List.of(
                        TestObjectFactory.createItemOption(
                                "색상",
                                List.of(
                                        TestObjectFactory.createDetailOption("블랙", 1000, 100),
                                        TestObjectFactory.createDetailOption("화이트", 2000, 200)
                                )
                        ),
                        TestObjectFactory.createItemOption(
                                "cpu",
                                List.of(
                                        TestObjectFactory.createDetailOption("4core", 20000, 100),
                                        TestObjectFactory.createDetailOption("8core", 40000, 50)
                                )
                        )
                )
        );
    }

}
