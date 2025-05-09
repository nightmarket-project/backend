package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestOptionFactory;
import store.nightmarket.itemcore.fixture.TestUserOptionFactory;
import store.nightmarket.itemcore.valueobject.ItemOptionId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionTest {

    @Test
    @DisplayName("buyOption의 userItemDetailOptions가 빈 리스트일때 Optional.empty를 반환한다")
    void shouldCreateOptionalEmptyWhenUserItemDetailOptionsIsEmpty() {
        UUID optionId = UUID.randomUUID();
        ItemOption option = TestOptionFactory.createItemOption(
                optionId,
                "색깔",
                TestOptionFactory.defaultDetailOption()
        );
        UserItemOption userItemOption = UserItemOption.newInstance(
                new ItemOptionId(optionId),
                Collections.emptyList()
        );

        Optional<UserItemOption> availableToBuy = option.isAvailableToBuy(userItemOption);
        assertThat(availableToBuy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("ItemOption, buyOption의 OptionId가 다르면 Optional empty를 반환한다.")
    void shouldCreateOptionalEmptyWhenOptionIdIsDifferent() {

        ItemOption option = TestOptionFactory.defaultOption();
        UserItemOption buyOption = TestUserOptionFactory.defaultUserOption();

        Optional<UserItemOption> availableToBuy = option.isAvailableToBuy(buyOption);

        assertThat(availableToBuy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("ItemOption, buyOption의 optionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 크면 " +
            "buyOption의 isPurchasable값이 true다")
    void canPurchaseWhenValidOptionAndEnoughStock() {
        ItemOptionTestData testData = createTestData(
                10, 10,
                2, 2
        );

        List<UserItemDetailOption> userDetailOptions = getAvailableToBuyOptions(testData);

        assertThat(userDetailOptions).hasSize(2);
        for (UserItemDetailOption option : userDetailOptions) {
            assertThat(option.isPurchasable()).isTrue();
        }
    }

    @Test
    @DisplayName("ItemOption, buyOption의 optionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 작으면 " +
            "buyOption의 isPurchasable값이 false다")
    void canNotPurchaseWhenValidOptionAndNotEnoughStock() {
        ItemOptionTestData testData = createTestData(
                10, 10,
                2, 20
        );

        List<UserItemDetailOption> userDetailOptions = getAvailableToBuyOptions(testData);

        assertThat(userDetailOptions).hasSize(2);
        assertThat(userDetailOptions.get(0).isPurchasable()).isTrue();
        assertThat(userDetailOptions.get(1).isPurchasable()).isFalse();
    }

    private List<UserItemDetailOption> getAvailableToBuyOptions(
            ItemOptionTestData testData
    ) {
        return testData.option.isAvailableToBuy(testData.userOption)
                .orElseThrow(() -> new ItemOptionException("옵션 불일치"))
                .getUserItemDetailOptions();
    }

    private ItemOptionTestData createTestData(
            int blackQty,
            int whiteQty,
            int blackUserQty,
            int whiteUserQty
    ) {
        UUID optionColorId = UUID.randomUUID();
        UUID blackId = UUID.randomUUID();
        UUID whiteId = UUID.randomUUID();

        ItemOption itemOption = TestOptionFactory.createItemOption(
                optionColorId,
                "색깔",
                TestOptionFactory.createDetailOption(
                        blackId,
                        "검은색",
                        1000,
                        blackQty
                ), TestOptionFactory.createDetailOption(
                        whiteId,
                        "하얀색",
                        2000,
                        whiteQty
                )
        );

        UserItemOption userItemOption = TestUserOptionFactory.createUserItemOption(
                optionColorId,
                TestUserOptionFactory.createUserItemDetailOption(
                        blackId,
                        blackUserQty
                ), TestUserOptionFactory.createUserItemDetailOption(
                        whiteId,
                        whiteUserQty
                )
        );

        return new ItemOptionTestData(itemOption, userItemOption);
    }

    private record ItemOptionTestData(ItemOption option, UserItemOption userOption) {
    }

}
