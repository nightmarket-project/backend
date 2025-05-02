package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.state.OptionState;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionTest {

    private final Name NAME = new Name("색상:블랙");
    private final Price PRICE = new Price(new BigDecimal("1000"));
    private final Quantity QUANTITY = new Quantity(new BigDecimal("100"));
    private final OptionState STATE = OptionState.BASIC;

    @Test
    @DisplayName("ItemOption은 이름, 가격, 수량, 상태로 생성된다")
    void newInstanceItemOptionSuccessfully() {
        ItemOption itemOption = newInstanceItemOption();

        assertThat(itemOption).isNotNull();
        assertThat(itemOption.getName()).isEqualTo(NAME);
        assertThat(itemOption.getPrice()).isEqualTo(PRICE);
        assertThat(itemOption.getQuantity()).isEqualTo(QUANTITY);
        assertThat(itemOption.getState()).isEqualTo(STATE);
    }

    private ItemOption newInstanceItemOption() {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()), NAME, PRICE, QUANTITY, STATE
        );
    }


}