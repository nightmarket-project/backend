package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemOptionGroupTest {

    @Test
    @DisplayName("ItemOptionGroup 성공적으로 객체가 생성된다")
    void shouldCreateItemOptionGroupSuccessfully() {
        ItemOptionGroup group = TestObjectFactory.defaultGroup();

        assertThat(group).isNotNull();
        assertThat(group).isInstanceOf(ItemOptionGroup.class);
    }

    @Test
    @DisplayName("ItemOptionGroup의 모든 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 크거나 같으면 구매 가능하다.")
    void shouldNotThrowExceptionWhenAllOptionIsGreaterThanOrEqualOtherOption() {
        ItemOptionGroup group = TestObjectFactory.defaultGroup();

        assertThatCode(() -> group.isAvailableToBuy(group))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ItemOptionGroup의 어떤 옵션 수량이 다른 ItemOptionGroup 옵션 수량보다 작으면 구매 불가능하다.")
    void shouldThrowExceptionWhenAnyItemOptionIsLessThanOtherOption() {
        ItemOptionGroup group = TestObjectFactory.defaultGroup();

        ItemOptionGroup otherGroup = createEmptyStockGroup();

        assertThatThrownBy(() -> group.isAvailableToBuy(otherGroup))
                .isInstanceOf(ItemOptionException.class);
    }

    private ItemOptionGroup createEmptyStockGroup() {
        return TestObjectFactory.createItemOptionGroup(
                "색상",
                List.of(
                        TestObjectFactory.createItemOption("블랙", 1000, 0),
                        TestObjectFactory.createItemOption("화이트", 2000, 0)
                )
        );
    }


}
