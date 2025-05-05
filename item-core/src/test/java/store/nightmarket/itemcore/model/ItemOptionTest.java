package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemOptionTest {

    @Test
    @DisplayName("ItemOption은 이름, 가격, 수량로 생성된다")
    void shouldCreateItemOptionWithNamePriceQuantity() {
        ItemOption itemOption = TestObjectFactory.defaultOption();

        assertThat(itemOption).isNotNull();
        assertThat(itemOption).isInstanceOf(ItemOption.class);
    }

    @Test
    @DisplayName("ItemOption이 구매하고 싶은 ItemOption Quantity 보다 작으면 구매할 수 없다.")
    void shouldThrowExceptionWhenOptionIsLessThanOtherOption() {
        ItemOption itemOption = TestObjectFactory.defaultOption();
        ItemOption buyItemOption = TestObjectFactory.createItemOption("블랙", 1000, 10);

        assertThatThrownBy(() -> itemOption.isAvailableToBuy(buyItemOption))
                .isInstanceOf(ItemOptionException.class);
    }


}
