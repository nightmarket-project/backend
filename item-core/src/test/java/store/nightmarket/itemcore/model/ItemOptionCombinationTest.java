package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionCombinationTest {

    @Test
    @DisplayName("ItemOptionCombination은 id, 이름, 종류로 구성된다.")
    void newInstanceItemOptionCombinationSuccessfully() {
        ItemOptionCombination option = newItemOptionCombination();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionCombination.class);
    }

    private ItemOptionCombination newItemOptionCombination() {
        return ItemOptionCombination.newInstance(
                new ItemOptionCombinationId(UUID.randomUUID()),
                new ItemId(UUID.randomUUID()),
                List.of(
                        ItemOptionGroup.newInstance(
                                new ItemOptionGroupId(UUID.randomUUID()),
                                new Name("색상"),
                                colorOptionList()),
                        ItemOptionGroup.newInstance(
                                new ItemOptionGroupId(UUID.randomUUID()),
                                new Name("사이즈"),
                                sizeOptionList()
                        )
                )
        );
    }

    private List<ItemOption> colorOptionList() {
        return List.of(
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("블랙"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(10))
                ),
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("화이트"),
                        new Price(BigDecimal.valueOf(2000)),
                        new Quantity(BigDecimal.valueOf(20))
                )
        );
    }

    private List<ItemOption> sizeOptionList() {
        return List.of(
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("100"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(10))
                ),
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("105"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(20))
                )
        );
    }



}