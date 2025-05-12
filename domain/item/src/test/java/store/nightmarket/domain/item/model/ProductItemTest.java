package store.nightmarket.domain.item.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.itemcore.model.UserItemDetailOption;
import store.nightmarket.itemcore.model.UserItemOption;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductItemTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("ProductItem, UserProductItem의  itemId가 다를때 Optional.empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenItemIdAndUserItemIdIsDifferent() {
        //given
        ProductItem productItem = TestItemFactory.defaultProductItem();
        UserProductItem userProductItem = TestItemFactory.defaultUserProductItem();

        //when
        Optional<UserProductItem> availableToBuy = productItem.isAvailableToBuy(userProductItem);

        //then
        assertThat(availableToBuy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("ProductItem, UserProductItem의 ItemId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 크면 " +
            "buyOption의 isPurchasable값이 true다")
    void canPurchaseWhenValidOptionAndEnoughStock() {
        //given
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                1, 1, 1, 1, 1, 1
        );

        //when
        UserProductItem userProductItem = getAvailableForPurchaseItem(testData);
        List<UserItemOption> computerPartsOption = userProductItem
                .getBasicOption()
                .getUserItemOptions();
        List<UserItemDetailOption> monitorOption = userProductItem
                .getAdditionalOption()
                .getUserItemDetailOptions();
        List<UserItemDetailOption> colorOption = computerPartsOption.getFirst().getUserItemDetailOptions();
        List<UserItemDetailOption> cpuOption = computerPartsOption.getLast().getUserItemDetailOptions();

        //then
        softly.assertThat(computerPartsOption).hasSize(2);
        softly.assertThat(colorOption).hasSize(2);
        softly.assertThat(cpuOption).hasSize(2);
        softly.assertThat(monitorOption).hasSize(2);
        softly.assertThat(colorOption).allMatch(UserItemDetailOption::isPurchasable);
        softly.assertThat(cpuOption).allMatch(UserItemDetailOption::isPurchasable);
        softly.assertThat(monitorOption).allMatch(UserItemDetailOption::isPurchasable);
        softly.assertAll();
    }

    @Test
    @DisplayName("ProductItem, UserProductItem의 ItemId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 작으면 " +
            "buyOption의 isPurchasable값이 false다")
    void canNotPurchaseWhenValidOptionAndNotEnoughStock() {
        //given
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                1, 1, 11, 1, 1, 11
        );

        //when
        UserProductItem userProductItem = getAvailableForPurchaseItem(testData);
        List<UserItemOption> computerOption = userProductItem
                .getBasicOption()
                .getUserItemOptions();
        List<UserItemDetailOption> monitorOption = userProductItem
                .getAdditionalOption()
                .getUserItemDetailOptions();
        List<UserItemDetailOption> colorOption = computerOption.getFirst().getUserItemDetailOptions();
        List<UserItemDetailOption> cpuOption = computerOption.getLast().getUserItemDetailOptions();

        //then
        softly.assertThat(computerOption).hasSize(2);
        softly.assertThat(colorOption).hasSize(2);
        softly.assertThat(cpuOption).hasSize(2);
        softly.assertThat(monitorOption).hasSize(2);
        softly.assertThat(colorOption).allMatch(UserItemDetailOption::isPurchasable);
        softly.assertThat(cpuOption.getFirst().isPurchasable()).isFalse();
        softly.assertThat(cpuOption.getLast().isPurchasable()).isTrue();
        softly.assertThat(monitorOption.getFirst().isPurchasable()).isTrue();
        softly.assertThat(monitorOption.getLast().isPurchasable()).isFalse();
        softly.assertAll();
    }

    private UserProductItem getAvailableForPurchaseItem(TestItemFactory.ProductItemTestData testData) {
        return testData.getProductItem().isAvailableToBuy(testData.getUserProductItem())
                .orElseThrow(() -> new ProductItemException("itemId 불일치"));
    }

}
