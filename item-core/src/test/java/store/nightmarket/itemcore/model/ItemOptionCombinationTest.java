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
    @DisplayName("ItemOptionCombination 성공적으로 객체가 생성된다.")
    void shouldCreateItemOptionCombination_Successfully() {
        ItemOptionCombination option = newItemOptionCombination();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionCombination.class);
    }

    @Test
    @DisplayName("ItemOptionCombination 모든 itemOption 수량이 0보다 크면 구매 가능하다.")
    void shouldReturnTrue_WhenItemOptionQuantityIsGreaterThanZero() {
        ItemOptionCombination option = newItemOptionCombination();
        assertThat(option.isAvailableToBuy()).isTrue();
    }

    @Test
    @DisplayName("ItemOptionCombination 어떤 itemOption 수량이 0이면 구매 불가하다.")
    void shouldReturnFalse_WhenAnyItemOptionHasZeroQuantity() {
        ItemOptionCombination option = newItemOptionCombinationWithZeroQuantity();
        assertThat(option.isAvailableToBuy()).isFalse();
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

    private ItemOptionCombination newItemOptionCombinationWithZeroQuantity() {
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
                                sizeOptionListWithZeroQuantity()
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

    private List<ItemOption> sizeOptionListWithZeroQuantity() {
        return List.of(
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("100"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(10))
                ),
                ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                        new Name("105"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.ZERO)
                )
        );
    }

}