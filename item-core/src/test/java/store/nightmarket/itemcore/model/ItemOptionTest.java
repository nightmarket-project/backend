package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemOptionTest {

    private final Name NAME = new Name("블랙");
    private final Price PRICE = new Price(new BigDecimal("1000"));
    private final Quantity QUANTITY = new Quantity(new BigDecimal("100"));
    private final Quantity ZERO_QUANTITY = new Quantity(BigDecimal.ZERO);

    @Test
    @DisplayName("ItemOption은 이름, 가격, 수량로 생성된다")
    void shouldCreateItemOptionWithNamePriceQuantity() {
        ItemOption itemOption = newInstanceItemOption();

        assertThat(itemOption).isNotNull();
        assertThat(itemOption).isInstanceOf(ItemOption.class);
    }

    @Test
    @DisplayName("ItemOption이 구매하고 싶은 ItemOption Quantity 보다 작으면 구매할 수 없다.")
    void shouldThrowExceptionWhenOptionIsLessThanOtherOption() {
        ItemOption itemOption = newInstanceItemOptionWithZeroQuantity();
        ItemOption buyItemOption = newInstanceItemOption();

        assertThatThrownBy(() -> itemOption.isAvailableToBuy(buyItemOption))
                .isInstanceOf(ItemOptionException.class);
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
