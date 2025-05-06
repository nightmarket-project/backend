package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemDetailOptionTest {

    @Test
    @DisplayName("ItemDetailOption은 이름, 가격, 수량로 생성된다")
    void shouldCreateItemOptionWithNamePriceQuantity() {
        ItemDetailOption itemDetailOption = TestObjectFactory.defaultOption();

        assertThat(itemDetailOption).isNotNull();
        assertThat(itemDetailOption).isInstanceOf(ItemDetailOption.class);
    }

    @Test
    @DisplayName("ItemDetailOption이 구매하고 싶은 ItemOption Quantity 보다 작으면 구매할 수 없다.")
    void shouldThrowExceptionWhenOptionIsLessThanOtherOption() {
        ItemDetailOption itemDetailOption = TestObjectFactory.createDetailOption("블랙", 1000, 0);
        ItemDetailOption buyItemDetailOption = TestObjectFactory.defaultOption();

        assertThatThrownBy(() -> itemDetailOption.isAvailableToBuy(buyItemDetailOption))
                .isInstanceOf(ItemOptionException.class);
    }


}
