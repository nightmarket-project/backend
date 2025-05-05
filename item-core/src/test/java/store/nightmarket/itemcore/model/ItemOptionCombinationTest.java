package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemOptionCombinationTest {

    @Test
    @DisplayName("ItemOptionCombination 성공적으로 객체가 생성된다.")
    void shouldCreateItemOptionCombinationSuccessfully() {
        ItemOptionCombination option = TestObjectFactory.defaultCombination();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionCombination.class);
    }

    @Test
    @DisplayName("ItemOptionCombination의 모든 수량이 다른 ItemOptionCombination 보다 크거나 같으면 구매 가능하다.")
    void shouldNotThrowExceptionWhenAllOptionQuantityIsGreaterThanOrOtherOptionQuantity() {
        ItemOptionCombination combination = TestObjectFactory.defaultCombination();
        ItemOptionCombination otherCombination = createEmptyStockCombination();

        assertThatCode(() -> combination.isAvailableToBuy(otherCombination))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ItemOptionCombination 어떤 itemOption 수량이 다른 ItemOptionCombination의 옵션보다 작으면 구매 불가하다.")
    void shouldThrowExceptionWhenAnyOptionQuantityIsLessThanOtherOptionQuantity() {
        ItemOptionCombination combination = createEmptyStockCombination();
        ItemOptionCombination otherCombination = TestObjectFactory.defaultCombination();

        assertThatThrownBy(() -> combination.isAvailableToBuy(otherCombination))
                .isInstanceOf(ItemOptionException.class);

    }

    private ItemOptionCombination createEmptyStockCombination() {
        return TestObjectFactory.createItemOptionCombination(
                List.of(
                        TestObjectFactory.createItemOptionGroup(
                                "색상",
                                List.of(
                                        TestObjectFactory.createItemOption("블랙", 1000, 100),
                                        TestObjectFactory.createItemOption("화이트", 2000, 200)
                                )
                        ),
                        TestObjectFactory.createItemOptionGroup(
                                "cpu",
                                List.of(
                                        TestObjectFactory.createItemOption("4core", 20000, 0),
                                        TestObjectFactory.createItemOption("8core", 40000, 50)
                                )
                        )
                )
        );
    }

}
