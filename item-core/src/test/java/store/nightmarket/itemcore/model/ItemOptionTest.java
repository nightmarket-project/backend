package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionTest {

    private final Name NAME = new Name("블랙");
    private final Price PRICE = new Price(new BigDecimal("1000"));
    private final Quantity QUANTITY = new Quantity(new BigDecimal("100"));
    private final Quantity ZERO_QUANTITY = new Quantity(BigDecimal.ZERO);

    @Test
    @DisplayName("ItemOption은 이름, 가격, 수량로 생성된다")
    void shouldCreateItemOption_WithNamePriceQuantity() {
        ItemOption itemOption = newInstanceItemOption();

        assertThat(itemOption).isNotNull();
        assertThat(itemOption).isInstanceOf(ItemOption.class);
    }

    @Test
    @DisplayName("수량이 0보다 크면 ItemOption을 구매할 수 있다.")
    void shouldReturnTrue_WhenQuantityIsGreaterThanZero() {
        ItemOption itemOption = newInstanceItemOption();

        boolean result = itemOption.isAvailableToBuy();
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("수량이 0이면 ItemOption을 구매할 수 없다.")
    void shouldReturnFalse_WhenQuantityIsZero() {
        ItemOption itemOption = newInstanceItemOptionWithZeroQuantity();

        boolean result = itemOption.isAvailableToBuy();
        assertThat(result).isFalse();
    }

    private ItemOption newInstanceItemOption() {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()), NAME, PRICE, QUANTITY
        );
    }

    private ItemOption newInstanceItemOptionWithZeroQuantity() {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()), NAME, PRICE, ZERO_QUANTITY
        );
    }

}