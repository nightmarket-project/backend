package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemOptionGroupTest {

    @Test
    @DisplayName("ItemOptionGroup성공적으로 객체가 생성된다.")
    void shouldCreateItemOptionCombinationSuccessfully() {
        ItemOptionGroup option = TestObjectFactory.defaultCombination();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionGroup.class);
    }

    @Test
    @DisplayName("ItemOptionGroup의 모든 수량이 다른 ItemOptionGroup 보다 크거나 같으면 구매 가능하다.")
    void shouldNotThrowExceptionWhenAllOptionQuantityIsGreaterThanOrOtherOptionQuantity() {
        ItemOptionGroup combination = TestObjectFactory.defaultCombination();
        ItemOptionGroup otherCombination = createEmptyStockGroup();

        assertThatCode(() -> combination.isAvailableToBuy(otherCombination))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ItemOptionGroup 어떤 itemOption 수량이 다른 ItemOptionGroup의 옵션보다 작으면 구매 불가하다.")
    void shouldThrowExceptionWhenAnyOptionQuantityIsLessThanOtherOptionQuantity() {
        ItemOptionGroup combination = createEmptyStockGroup();
        ItemOptionGroup otherCombination = TestObjectFactory.defaultCombination();

        assertThatThrownBy(() -> combination.isAvailableToBuy(otherCombination))
                .isInstanceOf(ItemOptionException.class);

    }

    private ItemOptionGroup createEmptyStockGroup() {
        return TestObjectFactory.createItemOptionGroup(
                List.of(
                        TestObjectFactory.createItemOption(
                                "색상",
                                List.of(
                                        TestObjectFactory.createDetailOption("블랙", 1000, 100),
                                        TestObjectFactory.createDetailOption("화이트", 2000, 200)
                                )
                        ),
                        TestObjectFactory.createItemOption(
                                "cpu",
                                List.of(
                                        TestObjectFactory.createDetailOption("4core", 20000, 0),
                                        TestObjectFactory.createDetailOption("8core", 40000, 50)
                                )
                        )
                )
        );
    }

}
