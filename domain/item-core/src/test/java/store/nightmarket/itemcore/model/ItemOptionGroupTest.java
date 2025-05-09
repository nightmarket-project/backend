package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.fixture.TestOptionFactory;
import store.nightmarket.itemcore.fixture.TestUserOptionFactory;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionGroupTest {

    @Test
    @DisplayName("ItemOptionGroup, buyOptionGroup의 optionId가 다를때" +
            " userGroup객체에는 빈 리스트 옵션이 생성된다,")
    void shouldCreateUserOptionGroupWithEmptyListWhenGroupIdIsDifferent() {
        ItemOptionGroup group = TestOptionFactory.defaultOptionGroup();
        UserItemOptionGroup userItemOptionGroup = TestUserOptionFactory.defaultUserOptionGroup();

        UserItemOptionGroup availableToBuy = group.isAvailableToBuy(userItemOptionGroup);
        assertThat(availableToBuy.getUserItemOptions()).isEmpty();
    }


    @Test
    @DisplayName("ItemOptionGroup, buyOptionGroup의 optionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 크면 " +
            "buyOption의 isPurchasable값이 true다")
    void canPurchaseWhenValidOptionAndEnoughStock() {
        ItemOptionTestData testData = createTestData(
                100, 200, 300,
                10, 20, 30
        );

        List<UserItemOption> userOptions = getAvailableToBuyOptions(testData);

        assertThat(userOptions).hasSize(2);
        assertThat(userOptions.get(0).getUserItemDetailOptions()).allMatch(UserItemDetailOption::isPurchasable);
        assertThat(userOptions.get(1).getUserItemDetailOptions()).allMatch(UserItemDetailOption::isPurchasable);
    }

    @Test
    @DisplayName("ItemOptionGroup, buyOptionGroup의 optionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 작으면 " +
            "buyOption의 isPurchasable값이 false다")
    void canNotPurchaseWhenValidOptionAndEnoughNotStock() {
        ItemOptionTestData testData = createTestData(
                100, 200, 300,
                1000, 20, 3000
        );

        List<UserItemOption> userOptions = getAvailableToBuyOptions(testData);

        assertThat(userOptions).hasSize(2);

        List<UserItemDetailOption> colorOptions = userOptions.get(0).getUserItemDetailOptions();
        List<UserItemDetailOption> cpuOptions = userOptions.get(1).getUserItemDetailOptions();

        assertThat(colorOptions.get(0).isPurchasable()).isFalse();
        assertThat(colorOptions.get(1).isPurchasable()).isTrue();
        assertThat(cpuOptions.get(0).isPurchasable()).isFalse();
    }

    private List<UserItemOption> getAvailableToBuyOptions(ItemOptionTestData data) {
        return data.group.isAvailableToBuy(data.userGroup)
                .getUserItemOptions();
    }

    private ItemOptionTestData createTestData(
            int blackQty,
            int whiteQty,
            int cpuQty,
            int blackUserQty,
            int whiteUserQty,
            int cpuUserQty
    ) {
        UUID groupId = UUID.randomUUID();
        UUID optionColorId = UUID.randomUUID();
        UUID optionCpuId = UUID.randomUUID();
        UUID blackId = UUID.randomUUID();
        UUID whiteId = UUID.randomUUID();
        UUID cpuId = UUID.randomUUID();

        ItemOptionGroup group = TestOptionFactory.createItemOptionGroup(
                groupId,
                TestOptionFactory.createItemOption(
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
                ), TestOptionFactory.createItemOption(
                        optionCpuId,
                        "cpu",
                        TestOptionFactory.createDetailOption(
                                cpuId,
                                "4코어",
                                3000,
                                cpuQty
                        )
                )
        );

        UserItemOptionGroup userGroup = TestUserOptionFactory.createUserItemOptionGroup(
                groupId,
                TestUserOptionFactory.createUserItemOption(
                        optionColorId,
                        TestUserOptionFactory.createUserItemDetailOption(
                                blackId,
                                blackUserQty
                        ), TestUserOptionFactory.createUserItemDetailOption(
                                whiteId,
                                whiteUserQty
                        )
                ),
                TestUserOptionFactory.createUserItemOption(
                        optionCpuId,
                        TestUserOptionFactory.createUserItemDetailOption(
                                cpuId,
                                cpuUserQty
                        )
                )
        );

        return new ItemOptionTestData(group, userGroup);
    }

    private record ItemOptionTestData(ItemOptionGroup group, UserItemOptionGroup userGroup) {}

}
