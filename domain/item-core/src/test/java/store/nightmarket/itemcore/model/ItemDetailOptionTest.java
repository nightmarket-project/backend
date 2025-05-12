package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.fixture.TestOptionFactory;
import store.nightmarket.itemcore.fixture.TestUserOptionFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDetailOptionTest {

    @Test
    @DisplayName("ItemDetailOption, buyDetailOption의 detailOptionId가 다르면 Optional empty를 반환한다.")
    void shouldCreateOptionalEmptyWhenOptionIdIsDifferent() {
        //given
        ItemDetailOption itemDetailOption = TestOptionFactory.defaultDetailOption();
        UserItemDetailOption buyDetailOption = TestUserOptionFactory.defaultUserDetailOption();

        //when
        Optional<UserItemDetailOption> availableToBuy = itemDetailOption.isAvailableToBuy(buyDetailOption);

        //then
        assertThat(availableToBuy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("ItemOption, buyDetailOption의 detailOptionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 크면 " +
            "buyOption의 isPurchasable값이 true다")
    void canPurchaseWhenValidOptionAndEnoughStock() {
        //given
        UUID optionId = UUID.randomUUID();
        ItemDetailOption option = TestOptionFactory.createDetailOption(
                optionId, "블랙", 1000, 10
        );
        UserItemDetailOption buyOption = TestUserOptionFactory.createUserItemDetailOption(
                optionId, 10
        );

        //when
        UserItemDetailOption isPurchasableOption = option.isAvailableToBuy(buyOption)
                .orElseThrow(() -> new ItemOptionException("option id 불일치"));

        //then
        assertThat(isPurchasableOption.isPurchasable()).isTrue();
    }

    @Test
    @DisplayName("ItemOption, buyDetailOption의 detailOptionId가 같고 " +
            "ItemOption Quantity가 buyOption Quantity 보다 작으면 " +
            "buyOption을 Optional로 감싸서 반환한다.")
    void canNotPurchaseWhenValidOptionAndNotEnoughStock() {
        //given
        UUID optionId = UUID.randomUUID();
        ItemDetailOption option = TestOptionFactory.createDetailOption(
                optionId, "블랙", 1000, 1
        );
        UserItemDetailOption buyOption = TestUserOptionFactory.createUserItemDetailOption(
                optionId, 10
        );

        //when
        UserItemDetailOption isPurchasableOption = option.isAvailableToBuy(buyOption)
                .orElseThrow(() -> new ItemOptionException("option id 불일치"));

        //then
        assertThat(isPurchasableOption.isPurchasable()).isFalse();
    }

}
