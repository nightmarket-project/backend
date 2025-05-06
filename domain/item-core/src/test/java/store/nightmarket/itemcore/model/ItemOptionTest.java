package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemOptionTest {

    @Test
    @DisplayName("ItemOption 성공적으로 객체가 생성된다")
    void shouldCreateItemOptionGroupSuccessfully() {
        ItemOption group = TestObjectFactory.defaultGroup();

        assertThat(group).isNotNull();
        assertThat(group).isInstanceOf(ItemOption.class);
    }

    @Test
    @DisplayName("ItemOption의 모든 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 크거나 같으면 구매 가능하다.")
    void shouldNotThrowExceptionWhenAllOptionIsGreaterThanOrEqualOtherOption() {
        ItemOption group = TestObjectFactory.defaultGroup();

        assertThatCode(() -> group.isAvailableToBuy(group))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ItemOption의 어떤 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 작으면 구매 불가능하다.")
    void shouldThrowExceptionWhenAnyItemOptionIsLessThanOtherOption() {
        ItemOption group = createEmptyStockOption();
        ItemOption otherGroup = TestObjectFactory.defaultGroup();


        assertThatThrownBy(() -> group.isAvailableToBuy(otherGroup))
                .isInstanceOf(ItemOptionException.class);
    }

    private ItemOption createEmptyStockOption() {
        return TestObjectFactory.createItemOption(
                "색상",
                List.of(
                        TestObjectFactory.createDetailOption("블랙", 1000, 0),
                        TestObjectFactory.createDetailOption("화이트", 2000, 0)
                )
        );
    }


}
