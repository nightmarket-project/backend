package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemOptionGroupTest {

    @Test
    @DisplayName("ItemOptionGroup 성공적으로 객체가 생성된다")
    void shouldCreateItemOptionGroupSuccessfully() {
        ItemOptionGroup option = newInstanceItemOptionGroup();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionGroup.class);
    }

    @Test
    @DisplayName("ItemOptionGroup의 모든 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 크거나 같으면 구매 가능하다.")
    void shouldNotThrowExceptionWhenAllOptionIsGreaterThanOrEqualOtherOption() {
        ItemOptionGroup group = newInstanceItemOptionGroup();
        ItemOptionGroup otherGroup = newInstanceItemOptionGroup();

        assertThatCode(()-> group.isAvailableToBuy(otherGroup))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ItemOptionGroup의 어떤 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 작으면 구매 불가능하다.")
    void shouldThrowExceptionWhenAnyItemOptionIsLessThanOtherOption() {
        ItemOptionGroup group = newInstanceItemOptionGroupWithZeroQuantity();
        ItemOptionGroup otherGroup = newInstanceItemOptionGroup();

        assertThatThrownBy(()-> group.isAvailableToBuy(otherGroup))
                .isInstanceOf(ItemOptionException.class);
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
