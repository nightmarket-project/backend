package store.nightmarket.domain.item.model;

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

    @Test
    @DisplayName("ProductItem, UserProductItem의  itemId가 다를때 Optional.empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenItemIdAndUserItemIdIsDifferent() {
        ProductItem productItem = TestItemFactory.defaultProductItem();
        UserProductItem userProductItem = TestItemFactory.defaultUserProductItem();

        Optional<UserProductItem> availableToBuy = productItem.isAvailableToBuy(userProductItem);

        assertThat(availableToBuy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("ProductItem, UserProductItem의 ItemId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 크면 " +
            "buyOption의 isPurchasable값이 true다")
    void canPurchaseWhenValidOptionAndEnoughStock() {
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                1, 1, 1, 1, 1, 1
        );

        UserProductItem userProductItem = TestItemFactory.getAvailableToBuyItem(testData)
                .orElseThrow(() -> new ProductItemException("itemId 불일치"));

        List<UserItemOption> computerPartsOption = userProductItem
                .getBasicOption()
                .getUserItemOptions();
        List<UserItemDetailOption> monitorOption = userProductItem
                .getAdditionalOption()
                .getUserItemDetailOptions();
        List<UserItemDetailOption> colorOption = computerPartsOption.getFirst().getUserItemDetailOptions();
        List<UserItemDetailOption> cpuOption = computerPartsOption.getLast().getUserItemDetailOptions();

        assertThat(computerPartsOption).hasSize(2);
        assertThat(colorOption).hasSize(2);
        assertThat(cpuOption).hasSize(2);
        assertThat(monitorOption).hasSize(2);

        assertThat(colorOption).allMatch(UserItemDetailOption::isPurchasable);
        assertThat(cpuOption).allMatch(UserItemDetailOption::isPurchasable);
        assertThat(monitorOption).allMatch(UserItemDetailOption::isPurchasable);
    }

    @Test
    @DisplayName("ProductItem, UserProductItem의 ItemId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 작으면 " +
            "buyOption의 isPurchasable값이 false다")
    void canNotPurchaseWhenValidOptionAndNotEnoughStock() {
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                1, 1, 11, 1, 1, 11
        );

        UserProductItem userProductItem = TestItemFactory.getAvailableToBuyItem(testData)
                .orElseThrow(() -> new ProductItemException("itemId 불일치"));

        List<UserItemOption> computerOption = userProductItem
                .getBasicOption()
                .getUserItemOptions();
        List<UserItemDetailOption> monitorOption = userProductItem
                .getAdditionalOption()
                .getUserItemDetailOptions();
        List<UserItemDetailOption> colorOption = computerOption.getFirst().getUserItemDetailOptions();
        List<UserItemDetailOption> cpuOption = computerOption.getLast().getUserItemDetailOptions();

        assertThat(computerOption).hasSize(2);
        assertThat(colorOption).hasSize(2);
        assertThat(cpuOption).hasSize(2);
        assertThat(monitorOption).hasSize(2);

        assertThat(colorOption).allMatch(UserItemDetailOption::isPurchasable);
        assertThat(cpuOption.getFirst().isPurchasable()).isFalse();
        assertThat(cpuOption.getLast().isPurchasable()).isTrue();
        assertThat(monitorOption.getFirst().isPurchasable()).isTrue();
        assertThat(monitorOption.getLast().isPurchasable()).isFalse();
    }

}
