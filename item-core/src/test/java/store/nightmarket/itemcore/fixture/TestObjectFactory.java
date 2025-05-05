package store.nightmarket.itemcore.fixture;

import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestObjectFactory {

    public static ItemOption createItemOption(
            String name,
            int price,
            int quantity
    ) {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()),
                new Name(name),
                new Price(new BigDecimal(price)),
                new Quantity(new BigDecimal(quantity))
        );
    }

    public static ItemOptionGroup createItemOptionGroup(
            String groupName,
            List<ItemOption> itemOptions
    ) {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()),
                new Name(groupName),
                new ArrayList<>(itemOptions)
        );
    }

    public static ItemOptionCombination createItemOptionCombination(
            List<ItemOptionGroup> itemOptionGroups
    ) {
        return ItemOptionCombination.newInstance(
                new ItemOptionCombinationId(UUID.randomUUID()),
                new ItemId(UUID.randomUUID()),
                new ArrayList<>(itemOptionGroups)
        );
    }

    public static ItemOption defaultOption() {
        return TestObjectFactory.createItemOption("블랙", 1000, 10);
    }

    public static ItemOptionGroup defaultGroup() {
        return TestObjectFactory.createItemOptionGroup(
                "색상",
                List.of(
                        TestObjectFactory.createItemOption("블랙", 1000, 10),
                        TestObjectFactory.createItemOption("화이트", 2000, 20)
                )
        );
    }

    public static ItemOptionCombination defaultCombination() {
        return TestObjectFactory.createItemOptionCombination(
                List.of(
                        TestObjectFactory.createItemOptionGroup(
                                "색상",
                                List.of(
                                        TestObjectFactory.createItemOption("블랙", 1000, 100),
                                        TestObjectFactory.createItemOption("화이트", 2000, 200)
                                )
                        ),
                        TestObjectFactory.createItemOptionGroup(
                                "cpu",
                                List.of(
                                        TestObjectFactory.createItemOption("4core", 20000, 100),
                                        TestObjectFactory.createItemOption("8core", 40000, 50)
                                )
                        )
                )
        );
    }

}
