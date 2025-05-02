package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemOptionGroupTest {

    @Test
    @DisplayName("ItemOptionGroup 성공적으로 객체가 생성된다")
    void newInstanceItemOptionGroupSuccessfully() {
        ItemOptionGroup option = newInstanceItemOptionGroup();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionGroup.class);
    }

    @Test
    @DisplayName("ItemOptionGroup의 모든 itemOption이 수량이 0보다 크기 때문에 구매 가능하다.")
    void isAvailableToBuySuccessfully() {
        ItemOptionGroup option = newInstanceItemOptionGroup();
        assertThat(option.isAvailableToBuy()).isTrue();
    }

    @Test
    @DisplayName("ItemOptionGroup의 어떤 itemOption이 수량이 0이기 때문에 구매 불가하다.")
    void isNotAvailableToBuyFailure() {
        ItemOptionGroup option = newInstanceItemOptionGroupWithZeroQuantity();
        assertThat(option.isAvailableToBuy()).isFalse();
    }

    private ItemOptionGroup newInstanceItemOptionGroup() {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()),
                new Name("색상"),
                List.of(
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
                )
        );
    }

    private ItemOptionGroup newInstanceItemOptionGroupWithZeroQuantity() {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()),
                new Name("색상"),
                List.of(
                        ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                                new Name("블랙"),
                                new Price(BigDecimal.valueOf(1000)),
                                new Quantity(BigDecimal.valueOf(10))
                        ),
                        ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                                new Name("화이트"),
                                new Price(BigDecimal.valueOf(2000)),
                                new Quantity(BigDecimal.ZERO)
                        )
                )
        );
    }
}